package com.openclassrooms.tourguide.user;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import gpsUtil.location.VisitedLocation;
import tripPricer.Provider;

public class User {
	private final UUID userId; // identifiant du user
	private final String userName; // pseudo du user
	private String phoneNumber; // téléhpne du user
	private String emailAddress; // email du user
	private Date latestLocationTimestamp; // date à laquelle il a été localisé en dernier

	//
	private List<VisitedLocation> visitedLocations = new CopyOnWriteArrayList<>(); // liste des lieux visités

	//
	private List<UserReward> userRewards = new CopyOnWriteArrayList<>(); // liste de l'historique des récompenses reçues
																			// par le user
	private UserPreferences userPreferences = new UserPreferences(); // liste des préférences (filtres) déterminés par
																		// le user
	private List<Provider> tripDeals = new CopyOnWriteArrayList<>(); // liste des offres (récompenses) du user

	public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public UUID getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setLatestLocationTimestamp(Date latestLocationTimestamp) {
		this.latestLocationTimestamp = latestLocationTimestamp;
	}

	public Date getLatestLocationTimestamp() {
		return latestLocationTimestamp;
	}

	public void addToVisitedLocations(VisitedLocation visitedLocation) {
		visitedLocations.add(visitedLocation);
	}

	public List<VisitedLocation> getVisitedLocations() {
		return visitedLocations;
	}

	public void clearVisitedLocations() {
		visitedLocations.clear();
	}

	public void addUserReward(UserReward userReward) {
		// anyMatch && .add pas atomiques -> risque de concurrence (!= thread-safe) => bloc de code synchronized 
		synchronized (userRewards) {
			// Verifie si l'utilisateur a déjà une récomprense pour cette attraction
			boolean hasRewardForAttraction = userRewards.stream()
					.anyMatch(r -> r.attraction.attractionName.equals(userReward.attraction.attractionName));
			if (!hasRewardForAttraction) {
				userRewards.add(userReward);
			}
		}
	}

	public List<UserReward> getUserRewards() {
		return userRewards;
	}

	public UserPreferences getUserPreferences() {
		return userPreferences;
	}

	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}

	public VisitedLocation getLastVisitedLocation() {
		if (visitedLocations.isEmpty()) {
			return null;
		}
		return visitedLocations.get(visitedLocations.size() - 1);
	}

	public void setTripDeals(List<Provider> tripDeals) {
		this.tripDeals = tripDeals;
	}

	public List<Provider> getTripDeals() {
		return tripDeals;
	}

}
