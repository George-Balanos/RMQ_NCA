//GEORGIOS MPALANOS

public class RMQ
{
	int[][] sparseTable = null;
	int[] array = null;
	
	public void initialize(int n, int[] array)
	{
		//initialize the RMQ structure in O(nlogn) time,
		//using O(nlogn) blocks of precomputed answers
		this.sparseTable = new int[n][log2(n)+1];
		this.array = array;
		
		for(int i = 0; i<n; i++){
			sparseTable[i][0] = i;
		}
		
		for(int j = 1; j <= log2(n); j++){
			for(int i =0; i + (int)Math.pow(2,j) -1 < n; i++){
				if(array[sparseTable[i][j-1]] <= array[sparseTable[i+(int)Math.pow(2,j-1)][j-1]]){
					sparseTable[i][j] = sparseTable[i][j-1];
				}else{
					sparseTable[i][j] = sparseTable[i+(int)Math.pow(2,j-1)][j-1];
				}
			}
		}	
	}

	public int rangeMin(int l, int r)
	{
		//return the min{array[l],...,array[r]} on O(1) time
		if(l>r){
			int temp = l;
			l = r;
			r = temp;
		}
		
		if(l == r){
			return l;
		}
	
		int k = log2(r - l + 1);
		
		
		if(array[sparseTable[l][k]] <= array[sparseTable[r - (int)Math.pow(2,k) + 1][k]]){
			return sparseTable[l][k];
		}
		
		return sparseTable[r - (int)Math.pow(2,k) + 1][k];
		
	}
	
	public int[] st;
	
	public void computeTree(int[] array,int n, int[] parentArray){
		st = new int[n];
		int i = -1;
		int k = -1;
		int top = -1;
		for(i =0; i<n; i++){
			k = top;
			while(k>=0 && array[st[k]] > array[i]){
				k--;
			}
			
			if(k != -1){
				parentArray[i] = st[k];
			}
			
			if(k < top){
				parentArray[st[k+1]] = i;
			}
			
			st[++k] = i;
			top = k;
			
		}
		
		parentArray[st[0]] = st[0];
		
	}


	

	public int[] rmq_offline(int n, int[] array, int[][] q)
	{
		//answer the range-minimum queries q using the reduction to the off-line NCA problem
		
		//somehow, you must make use of the function NCA.nca_offline
		int[] resultsNCA = new int[q.length];
		NCA myNCA = new NCA();
		int[] parents = new int[n];
		computeTree(array,n,parents);
		resultsNCA = myNCA.nca_offline(n,parents,q);
		
		return resultsNCA;
	}
	
	public int log2(int N)
    {
        int result = (int)(Math.log(N) / Math.log(2));
  
        return result;
    }
	
	public static void main(String[] args)
   {      
		In.init();
		int n = Integer.parseInt(In.getString());
		int[] A = new int[n];
		int index = 0;
		while (!In.empty())
		{
			A[index++] = Integer.parseInt(In.getString());
		}
		RMQ rmq = new RMQ();
		rmq.initialize(n,A); 
		n--;
		System.out.println("Answering queries on-line");
		System.out.println("range_minimum(0,3) = "+A[rmq.rangeMin(0,3)]);
		System.out.println("range_minimum(2,3) = "+A[rmq.rangeMin(2,3)]);
		System.out.println("range_minimum(1,4) = "+A[rmq.rangeMin(1,4)]);
		System.out.println();
		
		int[][] queries = new int[4][];
		for(int i=0; i<4; i++)
		{
			queries[i] = new int[2];
		}
		queries[0][0]=0; queries[0][1]=3;
		queries[1][0]=2; queries[1][1]=3;
		queries[2][0]=1; queries[2][1]=4;
		queries[3][0]=1; queries[3][1]=2;
		System.out.println("Calculating all queries off-line");
		int[] results = rmq.rmq_offline(n,A,queries);
		for(int i=0; i<4; i++)
		{
			System.out.println("range_minimum("+queries[i][0]+","+queries[i][1]+") = "+A[results[i]]);
		}
   }
}

