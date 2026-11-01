package it.usna.shellyscan.model.device.g1.modules;

import java.io.IOException;

import it.usna.shellyscan.model.device.g1.AbstractG1Device;
import it.usna.shellyscan.model.device.modules.InputManager;
import tools.jackson.databind.JsonNode;

/**
 * InputResetManager model implementation for G1
 */
public class InputManagerG1 implements InputManager {
	private final AbstractG1Device parent;
	private ResetMode resetMode;
	
	public InputManagerG1(AbstractG1Device d) throws IOException {
		this(d, d.getJSON("/settings"));
	}
	
	/**
	 *  use this constructor if "settings" is known in order to decrease the device effort
	 * @param d the device
	 * @param settings JsonNode configuration node
	 */
	public InputManagerG1(AbstractG1Device d, JsonNode config) {
		this.parent = d;
		JsonNode reset = config.path("factory_reset_from_switch");
		if(reset.isMissingNode()) {
			resetMode = ResetMode.NOT_APPLICABLE;
		} else if(reset.booleanValue(false)) {
			resetMode = ResetMode.TRUE;
		} else {
			resetMode = ResetMode.FALSE;
		}
	}
	
	@Override
	public ResetMode getResetMode() {
		return resetMode;
	}

	@Override
	public Boolean getResetModeAsBoolean() {
		if(resetMode == ResetMode.TRUE) {
			return true;
		} else if(resetMode == ResetMode.FALSE) {
			return false;
		} else {
			return null;
		}
	}
	
	@Override
	public String enableReset(boolean enable) {
		if(resetMode == ResetMode.NOT_APPLICABLE) {
			return "notApplicable";
		} else {
			return parent.sendCommand("/settings?factory_reset_from_switch=" + enable);
		}
	}
}
