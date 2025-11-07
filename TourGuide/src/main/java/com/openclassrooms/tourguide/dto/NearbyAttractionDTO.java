package com.openclassrooms.tourguide.dto;

import gpsUtil.location.Location;
/** 
 * DTO représentant une attraction à proximité d'un utilisateur
 * Cette classe est utilisée pour transférer les informations essentielles sur une attraction
 * (nom, localisation, distance de l'utilisateur et points de récompense) entre les couches
 * de l'application, notamment pour l'affichage ou l'exposition via une API.
 */
public class NearbyAttractionDTO {

	private String attractionName;
	private Location attractionLocation;
	private Location userLocation;
	private double distanceToAttractionInMiles;
	private int rewardPoints;
	
	public NearbyAttractionDTO() {

	}

	public NearbyAttractionDTO(String attractionName, Location attractionLocation, Location userLocation,
			double distanceToAttractionInMiles, int rewardPoints) {
		this.attractionName = attractionName;
		this.attractionLocation = attractionLocation;
		this.userLocation = userLocation;
		this.distanceToAttractionInMiles = distanceToAttractionInMiles;
		this.rewardPoints = rewardPoints;
	}

	public Location getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(Location userLocation) {
		this.userLocation = userLocation;
	}

	public Location getAttractionLocation() {
		return attractionLocation;
	}

	public void setAttractionLocation(Location attractionLocation) {
		this.attractionLocation = attractionLocation;
	}

	public String getAttractionName() {
		return attractionName;
	}

	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

	public double getDistanceToAttractionInMiles() {
		return distanceToAttractionInMiles;
	}

	public void setDistanceToAttractionInMiles(double distanceToAttractionInMiles) {
		this.distanceToAttractionInMiles = distanceToAttractionInMiles;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
}
