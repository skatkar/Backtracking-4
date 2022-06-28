import java.util.LinkedList;
import java.util.Queue;

public class BuildingPlacement {
    int H,W,n;
    int minDistance;
    public BuildingPlacement(int H, int W, int n){
        this.H = H;
        this.W = W;
        this.n = n; // number of buildings to place in a given grid
        minDistance = Integer.MAX_VALUE;
    }

    // TC : O(HW P n * HW) -> (factorial of(HW) / factorial of (n) ) * HW
    // SC : O(H * W)
    public int computeDistance() {
        int[][] grid = new int[H][W];

        // Initialize the grid first
        for(int i=0; i < H; i++) {
            for(int j=0; j < W; j++){
                grid[i][j] = -1;
            }
        }

        backtracking(grid, n, 0,0);
        
        return minDistance;
    }

    private void backtracking(int[][] grid, int n, int r, int c) {
        // base
        if(n == 0){
            bfs(grid);
            return;
        }

        // logic
        if(c == W) { // if c reaches the end of column, reset r and c
            r++;
            c = 0;
        }

        for(int i=r; i < H; i++) {
            for(int j=c; j < W; j++) {
                //action - to place a building
                grid[i][j] = 0;

                // recurse
                backtracking(grid, n -1, i, j+1);

                // backtrack
                grid[i][j] = -1;
            }
        }
    }

    private void bfs(int[][] grid) {
        boolean[][] visited = new boolean[H][W];
        Queue<int[]> q = new LinkedList<>();

        // Look for the building to put to the queue
        for(int i=0; i< H; i++) {
            for(int j=0; j < W; j++){
                if(grid[i][j] == 0){
                    q.add(new int[]{i,j});
                    visited[i][j] = true;
                }
            }
        }

        int[][] dirs = new int[][]{
                {0,1},
                {0,-1},
                {1,0},
                {-1,0}
        };

        int distance = 0;

        while (!q.isEmpty()){
            int size = q.size();
            for(int i=0; i < size;i++) {
                int[] curr = q.poll();
                for(int[] dir : dirs) {
                    int nr = curr[0] + dir[0];
                    int nc = curr[1] + dir[1];
                    if(nr >= 0 && nc >= 0 && nr < H && nc < W && grid[nr][nc] == -1){
                        visited[nr][nc] = true;
                        q.add(new int[]{nr,nc});
                    }
                }
            }
            distance++;
        }
        minDistance = Math.min(minDistance, distance - 1);
    }
}
