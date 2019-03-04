public class TestTest{
	public static void main(String[] args){
		int game_size = 104;
		int nRows = game_size/4;
		int nCols = 14;

		char[][] board = new char[nRows][nCols];
		
		for (int i = 0; i < nRows; i++){
			int count = 1;
			for (int j = 0; j < nCols; j++){
				if (j == 0) 
					board[i][j] = (char) (i + 'a');	
				else if(j % 3 == 1){
					board[i][j] = (char) (count + '0');
					count++;
				}
				else if (j % 3 == 2)
					board[i][j] = ' ';
				else if (j % 3 == 0)
					board[i][j] = (char) (i + 'a');

			}
			board[i][12] = ' ';
			board[i][13] = '\n';
		}

		int nChars = nRows * nCols;
		char[] m_board = new char[nChars];
		int a_count = 0;
		for (int i = 0; i < nRows; i++){
			for (int j = 0; j < nCols; j++){
				m_board[a_count] = board[i][j];
				a_count++;
			}

		}


		for (int i = 0; i < nChars; i++)
			System.out.print(m_board[i]);

	//for (int i = 0; i < nRows; i++){
	//	for (int j = 0; j <nCols; j++){
	//		System.out.print(board[i][j]);
	//	}
	//}
	}
}