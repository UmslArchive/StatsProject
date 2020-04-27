package Core;

import com.google.api.services.sheets.v4.model.ValueRange;

public class Table {
    private static final int DEFAULT_TABLE_SZ = 17;
    private static final String DELIMITER = "|";

    private int rows, cols;
    private String tableArray[][];


    public Table() {
        rows = 0;
        cols = 0;
        tableArray = new String[DEFAULT_TABLE_SZ][DEFAULT_TABLE_SZ];
    }

    public Table(final int rows, final int cols) {
        this.rows = rows;
        this.cols = cols;
        tableArray = new String[rows][cols];
    }

    public Table(final String[][] tableArray) {
        rows = tableArray[0].length;
        cols = tableArray.length;
        this.tableArray = new String[rows][cols];
    }

    public Table(final ValueRange valueRange) {
        rows = valueRange.getValues().size();
        cols = valueRange.getValues().get(0).size();
        tableArray = new String[rows][cols];

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                tableArray[i][j] = valueRange.getValues().get(i).get(j).toString();
            }
        }
    }

    public void export() {

    }

    public void print() {
        System.out.println();
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                if(j != cols -1)
                    System.out.print(tableArray[i][j] + DELIMITER);
                else
                    System.out.print(tableArray[i][j]);

            }
            System.out.println();
        }
    }
}
