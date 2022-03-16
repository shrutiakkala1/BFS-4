Minesweeper
//Time complexity : O(M * N)
//Space complexity: O(M + N) 


class Solution {
    private static final int[][] DIRS = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 } };

    public char[][] updateBoard(char[][] board, int[] click) {
        if (board == null || click == null) {
            throw new IllegalArgumentException("Inputs are null");
        }
        if (board[click[0]][click[1]] != 'M' && board[click[0]][click[1]] != 'E') {
            return board;
        }
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }

        int mines = getMinesCount(board, click[0], click[1]);
        if (mines != 0) {
            board[click[0]][click[1]] = (char) (mines + '0');
            return board;
        }
        board[click[0]][click[1]] = 'B';

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(click);
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] d : DIRS) {
                int x = cur[0] + d[0];
                int y = cur[1] + d[1];
                if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'E') {
                    continue;
                }
                mines = getMinesCount(board, x, y);
                if (mines != 0) {
                    board[x][y] = (char) (mines + '0');
                    continue;
                }
                board[x][y] = 'B';
                queue.offer(new int[] { x, y });
            }
        }

        return board;
    }

    private int getMinesCount(char[][] board, int x, int y) {
        int mines = 0;
        for (int[] d : DIRS) {
            int r = x + d[0];
            int c = y + d[1];
            if (r >= 0 && r < board.length && c >= 0 && c < board[0].length && board[r][c] == 'M') {
                mines++;
            }
        }
        return mines;
    }
}


//Snakes and ladder
//Time complexity : O(N)
//Space complexityO(1)

class Solution {
    public int snakesAndLadders(int[][] board) {
        final int n = board.length;
        final int finish = n * n;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        boolean[] visited = new boolean[finish + 5];
        visited[0] = true;
        int moves = 1;
        
        while (!queue.isEmpty()) {
            int queueN = queue.size();
            for (int i = 0; i < queueN; i++) {
                int current = queue.poll();
                //track the max normal roll (no snakes or ladders)
                int maxNormal = -1;
                for (int next = current + 1; next <= current + 6; next++) {
                    int event = board(board, next);
                    if (event == -1) {
                        //no snakes or ladders => update max normal roll
                        maxNormal = next;
                    } else {
                        if (!visited[event - 1]) {
                            //check if reached finish
                            if (event >= finish) {
                                return moves;
                            }
                            //add any events to queue
                            visited[event - 1] = true;
                            queue.add(event);
                        }
                    }
                }
                if (maxNormal != -1) {
                    if (!visited[maxNormal - 1]) {
                        //check if reached finish
                        if (maxNormal >= finish) {
                            return moves;
                        }
                        //add max normal roll to queue
                        visited[maxNormal - 1] = true;
                        queue.add(maxNormal);
                    }
                }
            }
            moves++;
        }
        return -1;
    }
    
    private int board(int[][] board, int square) {
        final int n = board.length;
        if (square >= n * n) {
            return -1;
        }
        int row = (n - 1) - (square - 1) / n;
        int col = (square - 1) % n;
        if (((square - 1) / n) % 2 == 1) {
            col = (n - 1) - col;
        }
        return board[row][col];
    }
}
