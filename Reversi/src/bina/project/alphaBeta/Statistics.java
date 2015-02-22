package bina.project.alphaBeta;

public class Statistics implements IStatistics {

	private long mStartTime;
	private long mTotalTime;
	
	private int mMaxBranchingFactor = 0;
	private int mSumBranchingFactor = 0;
	
	private int mMaxDepth = 0;
	private int mSumDepth = 0;
	
	private int mNumOfNodes= 0;
	
	
	
	@Override
	public void startMonitoring() {
		mStartTime = System.currentTimeMillis();
	}
	
	@Override
	public void visitNode(int depth, int branch) {
		addBranchFactor(branch);
		addDepth(depth);
		mNumOfNodes++;
	}
	
	@Override
	public void endMonitoring() {
		mTotalTime = System.currentTimeMillis() - mStartTime;
	}

	private void addBranchFactor(int b){
		mMaxBranchingFactor = Math.max(b, mMaxBranchingFactor);
		mSumBranchingFactor += b;
		
	}
	
	public int getMaxBrnchingFactor(){
		return mMaxBranchingFactor;
	}
	
	public int getAvgBrnchingFactor(){
		if(mNumOfNodes==0){
			return 0;
		}
		return mSumBranchingFactor/mNumOfNodes;
	}
	
	
	private void addDepth(int d){
		mMaxDepth = Math.max(d, mMaxDepth);
		mSumDepth += d;
	}
	
	public int getMaxDepth(){
		return mMaxDepth;
	}
	
	public int getAvgDepth(){
		if(mNumOfNodes==0){
			return 0;
		}
		return mSumDepth/mNumOfNodes;
	}
	
	public long getTotalTime(){
		return mTotalTime;
	}
	
	public int getNumOfNodesVisited(){
		return mNumOfNodes;
	}

	
}
