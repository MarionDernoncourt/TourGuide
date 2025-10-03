package com.openclassrooms.tourguide.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.tourguide.user.User;
import com.openclassrooms.tourguide.user.UserReward;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import jakarta.annotation.PreDestroy;
import rewardCentral.RewardCentral;

@Service
public class RewardsService {

	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
	private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final GpsUtil gpsUtil;
	private final RewardCentral rewardsCentral;

	private final ExecutorService executorService = Executors.newFixedThreadPool(200);

	public RewardsService(GpsUtil gpsUtil, RewardCentral rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}

	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	public void calculateRewards(User user) {
		// Récupère les lieux visités par l'utilisateur et toutes les attractions de
		// manière safe-thread
		List<VisitedLocation> userLocations = new CopyOnWriteArrayList<VisitedLocation>(user.getVisitedLocations());
		List<Attraction> attractions = new CopyOnWriteArrayList<Attraction>(gpsUtil.getAttractions());

		// Concurrent Set pour éviter les doublons de récompense
		Set<String> rewardedAttractions = ConcurrentHashMap.newKeySet();
		rewardedAttractions.addAll(
				user.getUserRewards().stream().map(a -> a.attraction.attractionName).collect(Collectors.toSet()));

		// Boucle séquentielle sur chaque lieu visité par l'utilisateur
		for (VisitedLocation visitedLocation : userLocations) {
			// Boucle séquentielle sur chaque attraction
			for (Attraction attraction : attractions) {
				// Vérifie si l'utilisateur est proche de l'attraction
				if (nearAttraction(visitedLocation, attraction)) {
					// Le ConcurrentHashSet gère la threadSafety pour l'ajout
					if (rewardedAttractions.add(attraction.attractionName)) {
						int points = getRewardPoints(attraction, user);
						// Ajoute la récompense si elle n'a pas encore été attribuée
						user.addUserReward(new UserReward(visitedLocation, attraction, points));

					}
				}
			}
		}

	}

	public void calculateRewardsForAllUsers(List<User> users) {
		// Lance le calcul des récompenses de manière asynchrone pour tous les
		// utilisateurs
		List<CompletableFuture<Void>> userFutures = users.stream()
				.map(user -> CompletableFuture.runAsync(() -> calculateRewards(user), executorService))
				.collect(Collectors.toList());
		// attends que tout les calculs soient terminés avant de continuer
		CompletableFuture.allOf(userFutures.toArray(new CompletableFuture[0])).join();
	}

	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}

	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(attraction, visitedLocation.location) > proximityBuffer ? false : true;
	}

	public int getRewardPoints(Attraction attraction, User user) {
		return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
	}

	public double getDistance(Location loc1, Location loc2) {
		double lat1 = Math.toRadians(loc1.latitude);
		double lon1 = Math.toRadians(loc1.longitude);
		double lat2 = Math.toRadians(loc2.latitude);
		double lon2 = Math.toRadians(loc2.longitude);

		double angle = Math
				.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

		double nauticalMiles = 60 * Math.toDegrees(angle);
		double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
		return statuteMiles;
	}

	@PreDestroy
	public void shutdownExecutor() {
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
		}
	}

}
