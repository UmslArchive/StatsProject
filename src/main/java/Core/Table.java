package Core;

import java.io.*;
import com.google.api.services.sheets.v4.model.ValueRange;

public class Table {

    private static final int DEFAULT_TABLE_SZ = 17;
    private static final String DELIMITER = "|";

    enum Direction {
        LEFT,
        RIGHT
    }

    private int rows, cols;

    private String[][] tableArray;


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

        for(int i = 0; i < rows; ++rows)
            System.arraycopy(tableArray[i], 0, this.tableArray[i], 0, cols);

    }

    public Table(final ValueRange valueRange) {
        rows = valueRange.getValues().size();
        cols = valueRange.getValues().get(0).size();
        tableArray = new String[rows][cols];

        for(int i = 0; i < rows; ++i)
            for(int j = 0; j < cols; ++j)
                tableArray[i][j] = valueRange.getValues().get(i).get(j).toString();
    }

    public void export(final String filepath) throws IOException {

        //Delete file if already exists
        File outputFile = new File(filepath);
        outputFile.delete();
        outputFile = null;

        FileOutputStream fout = null;

        try {
            fout = new FileOutputStream(filepath);

            for(int i = 0; i < rows; ++i) {
                for(int j = 0; j < cols; ++j) {
                    if(j != cols -1)
                        fout.write((tableArray[i][j] + DELIMITER).getBytes());
                    else
                        fout.write(tableArray[i][j].getBytes());

                }
                if(i != rows - 1)
                    fout.write("\n".getBytes());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fout != null)
                fout.close();
        }
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
            if(i != rows -1)
                System.out.println();
        }
    }

    public int getColumnByFieldName(final String field) {
        for(int col = 0; col < cols; ++col) {
            if(tableArray[0][col].equals(field)) {
                return col;
            }
        }
        return -1;
    }

    public String getValue(int row, int col) {
        if(row < 0 || row > rows - 1 || col < 0 || col > cols - 1) {
            System.out.println("ERROR: Invalid table coordinate selection");
            return "ERROR_OOB";
        }

        return tableArray[row][col];
    }
    public void setValue(int row, int col, String value) {
        if(row < 0 || row > rows - 1 || col < 0 || col > cols - 1) {
            System.out.println("ERROR: Invalid table coordinate selection");
            return;
        }

        tableArray[row][col] = value;
    }

    public int addEmptyColumnAt(final int column, final Direction direction) {
        if(column < 0 || column > cols - 1) {
            System.out.println("ERROR: Couldn't add new column");
            return -1;
        }

        //Construct new tableArray w/ extra column:

        //Determine new column's index
        int extraColumnIndex;
        if(direction.equals(Direction.LEFT)) {
            extraColumnIndex = column;
        }
        else {
            extraColumnIndex = column + 1;
        }

        //Allocate new table array
        int columnsExtra = cols + 1;
        String[][] extraColumnTableArray = new String[rows][columnsExtra];

        //Copy the columns to new tableArray
        String[] currentColumn = new String[rows];
        for(int col = 0; col < columnsExtra; ++col) {

            //Get the current column
            if(col <= extraColumnIndex)
                currentColumn = getColumn(col);
            else
                currentColumn = getColumn(col - 1);

            //Copy each row of the currentColumn into the new tableArray
            for(int row = 0; row < rows; ++row) {

                if(col < extraColumnIndex) {
                    extraColumnTableArray[row][col] = currentColumn[row];
                }
                else if(col == extraColumnIndex) {
                    extraColumnTableArray[row][col] = "null";
                }
                else if( col > extraColumnIndex) {
                    extraColumnTableArray[row][col] = currentColumn[row];
                }
            }
        }

        this.tableArray = extraColumnTableArray;

        return extraColumnIndex;
    }

    private String[] getColumn(final int col) {
        if(col < 0 || col > cols - 1) {
            System.out.println("ERROR: Invalid column selection");
            return null;
        }

        String[] columnArray = new String[rows];

        //Copy the value of each row of the column into the columnArray
        for(int row = 0; row < rows; ++row) {
            columnArray[row] = tableArray[row][col];
        }

        return columnArray;
    }

    public int rows() { return rows; }
    public int cols() { return cols; }
}
