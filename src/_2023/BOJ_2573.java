package _2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2573 {
    static int N, M;  // 세로 크기 N, 가로 크기 M
    static int[][] mat;  // 현재 빙하의 높이를 저장하는 2차원 배열
    static int[][] aging;  // 빙하가 녹는 정도를 저장하는 2차원 배열
    static boolean[][] visited;  // 해당 위치의 빙하를 이미 방문했는지 여부를 저장하는 2차원 배열
    final static int[] dr = {-1, 0, 1, 0};  // 상하좌우로 이동할 때 사용하는 row 방향 배열
    final static int[] dc = {0, 1, 0, -1};  // 상하좌우로 이동할 때 사용하는 column 방향 배열


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strings = br.readLine().split(" ");
        N = Integer.parseInt(strings[0]);
        M = Integer.parseInt(strings[1]);
        mat = new int[N][M];
        int result = 0;
        int year = 0;
        // 초기 빙하 높이 설정
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int height = Integer.parseInt(st.nextToken());
                mat[i][j] = height;
            }
        }

        // 빙하가 모두 녹을 때까지 녹이는 과정 반복
        while (true) {
            int icebergCnt = 0; // 빙하 개수
            aging = new int[N][M];
            visited = new boolean[N][M];

            // 모든 위치를 방문하면서 빙하 개수 세기
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (mat[i][j] > 0 && !visited[i][j]) {
                        icebergCnt++;
                        bfs(new Position(i, j)); // 빙하를 방문하면서 주변 빙하를 녹임
                    }
                }
            }

            // 빙하가 없거나 두 개 이상이면 종료
            if (icebergCnt != 1){
                if(icebergCnt > 1){
                    result = year;
                }
                break;
            }

            // 빙하 녹이기
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    mat[i][j] = Math.max(mat[i][j] - aging[i][j], 0);
                }
            }
            year++;
        }
        System.out.println(result);
    }
    // 해당 위치가 배열 범위 내에 있는지 확인
    public static boolean isBoundary(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < M;
    }

    // 주어진 위치에서 시작해서 BFS 수행
    public static void bfs(Position startPosition) {
        Queue<Position> queue = new LinkedList<>();
        queue.offer(startPosition);
        visited[startPosition.row][startPosition.col] = true;
        while (!queue.isEmpty()) {
            Position currentPosition = queue.poll();
            int currentRow = currentPosition.row;
            int currentCol = currentPosition.col;
            for (int i = 0; i < 4; i++) {
                int nextRow = currentRow + dr[i];
                int nextCol = currentCol + dc[i];
                if (isBoundary(nextRow, nextCol)) {
                    // 주변 바다면 개수
                    if (mat[nextRow][nextCol] == 0) {
                        aging[currentRow][currentCol]++;
                    } else {
                        // 빙하가 있는 위치면 방문
                        if (!visited[nextRow][nextCol]) {
                            visited[nextRow][nextCol] = true;
                            queue.offer(new Position(nextRow, nextCol));
                        }
                    }

                }
            }
        }
    }

    // 위치를 나타내는 클래스
    static class Position {
        int row;
        int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
