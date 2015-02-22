package bina.project.alphaBeta;

/**
 * 
 * This class gathers statistics for a single turn.
 *
 */
public interface IStatistics {

	public void startMonitoring();
	
	public void visitNode(int depth, int branch);
	
	public void endMonitoring();
	
}
