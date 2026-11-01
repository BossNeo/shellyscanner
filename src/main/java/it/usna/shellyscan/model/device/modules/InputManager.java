package it.usna.shellyscan.model.device.modules;

public interface InputManager {
	enum ResetMode {TRUE, FALSE, NOT_APPLICABLE, MIX};
	
	ResetMode getResetMode();
	
	Boolean getResetModeAsBoolean();
	
	String enableReset(boolean enable);
}
