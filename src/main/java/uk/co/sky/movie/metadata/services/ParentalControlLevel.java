package uk.co.sky.movie.metadata.services;

public enum ParentalControlLevel {

	LEVEL_U("U"),
	LEVEL_PG("PG"), 
	LEVEL_12("12"), 
	LEVEL_15("15"), 
	LEVEL_18("18");
	
	private String level;

	/**
	 * @param level
	 */
	private ParentalControlLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return level;
	}

	public static ParentalControlLevel fromValue(String level) {
		for(ParentalControlLevel parentalControlLevel : ParentalControlLevel.values()) {
			if(parentalControlLevel.getLevel().equals(level)) {
				return parentalControlLevel;
			}
		}
		return null;
	}
}
