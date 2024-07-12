//GEORGIOS MPALANOS

public class NCA
{
	class Node{
		int value;
		Node parent;
		Node next;
		Node nextOfflineNode;
		boolean markedUN = false;
		boolean marked = false;
		
		public Node(int v){
			this.value = v;
		}
		
		public Node(int v, Node next){
			this.value = v;
			this.next = next;
		}
		
		public void setParent(Node parentNode){
			this.parent = parentNode;
		}
		
		public Node addOfflineNode(int v) {
			Node t = new Node(v);
			if (this.nextOfflineNode == null){ 
				this.nextOfflineNode = t;
				return t;
			}
			
			Node temp = this.nextOfflineNode;
			while(temp.nextOfflineNode != null){
				temp = temp.nextOfflineNode;
			}
			
			temp.nextOfflineNode = t;
			return t;
		}	
		
		public Node addNode(Node x, int v) {
			Node t = new Node(v);
			if (x == null){ 
				t.next = t;
			}else{ 
				t.next = x.next;
				x.next = t; 
			}
			return t;
		}	
	}
	
	private int n;
	private int p[];
	private int[] eulerTour;
	private int[] levelOfNode;
	private int[] representatives;
	private int index = 0;
	private Node root;
	public Node[] nodes;
	RMQ myRMQ = new RMQ();
	
	public void initialize(int n, int[] p)
	{
		//initialize the NCA structure for the on-line queries;
		//construct an Euler tour of the tree, and initialize an RMQ structure
		this.n = n;
		this.p = p;
		this.eulerTour = new int[2*n-1];
		this.levelOfNode = new int[2*n-1];
		this.representatives = new int[n];
		
		root = createTree();
		DFS(root.value);
		eulerTour[index] = root.value;
		
		for(int i = 0; i < eulerTour.length; i++){
			int eulerNum = eulerTour[i];
			levelOfNode[i] = computeHeight(eulerNum);
		}
		
		for(int i = 0;i < eulerTour.length; i++){
			int nodeOnTour = eulerTour[i];
			representatives[nodeOnTour] = i;
		} 
		
		for(int i = 0;i < eulerTour.length; i++){
			int nodeOnTour = eulerTour[i];
			if(representatives[nodeOnTour] > i){
				representatives[nodeOnTour] = i;
			}
		}
		
		myRMQ.initialize(2*n-1,levelOfNode);
		return;
		
	}
	
	public int computeHeight(int nodeR){
		int height = 0;
		while(p[nodeR] != nodeR){
			height++;
			nodeR = p[nodeR];
		}
		return height;
	}
	
	public void DFS(int i){
		
		if(nodes[i] == null){
			return;
		}
		
		if(nodes[i] != root){
			eulerTour[index++] = nodes[i].parent.value;
		}
		
		while(nodes[i].next != null){
			DFS(nodes[i].next.value);
			nodes[i] = nodes[i].next;
			eulerTour[index++] = nodes[i].value;
		}
	}
	
	
	public Node createTree(){
		Node[] nodes = new Node[n];
		for(int i=0; i < n; i++){
			nodes[i] = new Node(i);
		}
		
		for(int i = 0; i < n; i++){
			Node parentNode = nodes[p[i]];
			
			if(p[i] == i){
				root = nodes[i];
				continue;
			}
			
			if(parentNode.next == null){
				parentNode.addNode(parentNode,i);
				nodes[i].setParent(parentNode);
				continue;
			}
			
			parentNode.addNode(parentNode.next,i);
			nodes[i].setParent(parentNode);
			continue;
			
			
		}
		
		this.nodes = nodes;
		return root;
		
	}

	public int nca(int v, int u)
	{
		//return the nca(v,u) on O(1) time using the RMQ structure you have initialized
		int indexRMQ = 0;
		if(representatives[v]<representatives[u]){
			indexRMQ = myRMQ.rangeMin(representatives[v],representatives[u]);
		}else{
			indexRMQ = myRMQ.rangeMin(representatives[u],representatives[v]);
		}
		
		int ans = eulerTour[indexRMQ];
		return ans;
	}
	
	public UnionFind myUnionFind;
	
	class UnionFind{
		int[] parents;
		int[] size;
		int[] repr;
		
		public UnionFind(int n){
			parents = new int[n];
			size = new int[n];
			repr = new int[n];
			for(int i =0; i< n; i++){
				parents[i] = i;
				size[i] = 1;
				repr[i] = i;
			}
		}
		
		public int find(int i){
			while(parents[i] != i){
				i = parents[i];
			}
			return i;
		}
		
		public void union(int i, int j){
			int p = find(i);
			int q = find(j);
			int pHeight = computeHeight(p);
			int qHeight = computeHeight(q);
			if(q == p){
				return;
			}
			
			if(size[p] < size[q]){
				parents[q] = p;
				size[q] = size[p] + size[q];
			}else{
				parents[p] = q;
				size[p]= size[p] + size[q];
			}
			
			if(pHeight > qHeight){
				repr[p] = q;
			}else{
				repr[q] = p;
			}
			
		}
		
	}
	
	
	
	
	
	
	public Node[] myArray;
	public Node myRoot;
	
	public Node createTreeUN(int[] p){
		Node[] nodes = new Node[p.length];
		for(int i=0; i < nodes.length; i++){
			nodes[i] = new Node(i);
		}
		
		for(int i = 0; i < p.length; i++){
			Node parentNode = nodes[p[i]];
			
			if(p[i] == i){
				root = nodes[i];
				continue;
			}
			
			if(parentNode.next == null){
				parentNode.addNode(parentNode,i);
				nodes[i].setParent(parentNode);
				continue;
			}
			
			parentNode.addNode(parentNode.next,i);
			nodes[i].setParent(parentNode);
			continue;
			
			
		}
		
		
		this.myArray = nodes;
		return root;
		
	}
	
	public void postOrder(int k){
		myArray[k].marked = true;
		for(Node t = myArray[k]; t != null; t = t.next){
			if(!t.marked){
				postOrder(t.value);
				
				
				Node x = myArray[t.value];
				while(x.nextOfflineNode != null){
					x = x.nextOfflineNode;
					
					if(myArray[x.value].markedUN == true){
						int valueOne = x.value;
						int valueTwo = myArray[t.value].value;
						int counter = 0;
						for(int z = 0; z < q.length; z++){
							if(q[z][0] == valueOne && q[z][1] == valueTwo){
								results[counter] = myUnionFind.find(x.value);
								continue;
							}else if(q[z][0] == valueTwo && q[z][1] == valueOne){
								results[counter] = myUnionFind.find(x.value);
								continue;
							}
							counter++;
						}
					}	
					
					if(x.value == myArray[t.value].value){
						myArray[t.value].markedUN = false;
					}
					
				}
				myUnionFind.union(myArray[t.value].value,p[myArray[t.value].value]);
				myArray[t.value].markedUN = true;
			}
			
		}
		
	}
	
	public int[] results;
	public int[][] q;
	
	public int[] nca_offline(int n, int[] p, int q[][])
	{
		//implementation of the off-line NCA algorithm of Tarjan, 
		//using a DSU with path-compression and union by rank (or size)
		results = new int[q.length];
		this.q = q;
		this.p = p;
		
		myUnionFind = new UnionFind(n);
		myRoot = createTreeUN(p);
		

		for(int i = 0; i < q.length; i++){
			if(myArray[q[i][0]].value == myArray[q[i][1]].value){
				myArray[q[i][0]].markedUN = true;
			}
			myArray[q[i][0]].addOfflineNode(myArray[q[i][1]].value);
			myArray[q[i][1]].addOfflineNode(myArray[q[i][0]].value);
		}
		
		postOrder(myRoot.value);
		return results;
	}
	
	public static void main(String[] args){
		In.init();
		int n = Integer.parseInt(In.getString());
		int[] p = new int[n];
		for(int x=0; x<n; x++) 
		{
			p[x] = x;
		}
		while (!In.empty())
		{
			int x = Integer.parseInt(In.getString());
			int y = Integer.parseInt(In.getString());
			p[x] = y;
		}
		NCA nca = new NCA();
		nca.initialize(n,p); 

		System.out.println("Answering queries on-line");
		System.out.println("nca(10,11) = "+nca.nca(10,11));
		System.out.println("nca(11,4) = "+nca.nca(11,4));
		System.out.println("nca(6,7) = "+nca.nca(6,7));
		System.out.println("nca(11,6) = "+nca.nca(11,6));
		System.out.println("nca(8,8) = "+nca.nca(8,8));
		System.out.println();
		int[][] queries = new int[6][];
		for(int i=0; i<6; i++)
		{
			queries[i] = new int[2];
		}
		queries[0][0]=10; queries[0][1]=11;
		queries[1][0]=11; queries[1][1]=4;
		queries[2][0]=6; queries[2][1]=7;
		queries[3][0]=11; queries[3][1]=6;
		queries[4][0]=10; queries[4][1]=3;
		queries[5][0]=8; queries[5][1]=8;
		int[] result = nca.nca_offline(n,p,queries);
		for(int i=0; i<6; i++)
		{
			System.out.println("nca("+queries[i][0]+","+queries[i][1]+") = "+result[i]);
		}
		
	}
}









