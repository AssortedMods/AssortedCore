package com.grim3212.assorted.machines.api.machines;

public enum MachineTier {
	BASIC("basic", 1.0F),
	INTERMEDIATE("intermediate", 0.75F),
	ADVANCED("advanced", 0.5F),
	EXPERT("expert", 0.25F);

	private final String name;
	private final float speedModifier;

	private MachineTier(String name, float speedModifier) {
		this.name = name;
		this.speedModifier = speedModifier;
	}

	public float getSpeedModifier() {
		return speedModifier;
	}

	public String getName() {
		return name;
	}
}
