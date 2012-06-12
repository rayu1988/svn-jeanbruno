/**
 * 
 */
package br.com.datawatcher.entity;

/**
 * @author carrefour
 *
 */
public class FolderMapping extends DataMapping {

	private String				canonicalPath;
	private String				regexFilter;
	
	public FolderMapping() { }
	
	public FolderMapping(String canonicalPath, String regexFilter) {
		this.canonicalPath = canonicalPath;
		this.regexFilter = regexFilter;
	}
	
	@Override
	public void startup() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void checkChange() {
		// TODO Auto-generated method stub
	}
	
	// GETTERS AND SETTERS //
	public String getCanonicalPath() {
		return canonicalPath;
	}
	public void setCanonicalPath(String canonicalPath) {
		this.canonicalPath = canonicalPath;
	}
	public String getRegexFilter() {
		return regexFilter;
	}
	public void setRegexFilter(String regexFilter) {
		this.regexFilter = regexFilter;
	}
}
