/**
 * 
 */
package br.com.datawatcher.entity;

/**
 * @author carrefour
 *
 */
public class CheckChange {

	private String cronExpression;
	
	public CheckChange() { }
	
	public CheckChange(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	// GETTERS AND SETTERS //
	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
}
