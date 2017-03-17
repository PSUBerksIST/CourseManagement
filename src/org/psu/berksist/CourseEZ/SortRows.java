
package org.psu.berksist.CourseEZ;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Robert Zwolinski
 */

// Purpose of this is to simplify sorting tables
public class SortRows {
    
    public static final SortOrder ASC = SortOrder.ASCENDING;
    public static final SortOrder DESC = SortOrder.DESCENDING;
    
    private List<RowSorter.SortKey> sortKeys = new ArrayList<>();
    private TableRowSorter<TableModel> sorter;
    
    // Pass in the JTable to work with
    public SortRows (JTable jtIn){
        
        sorter = new TableRowSorter<>(jtIn.getModel());
        jtIn.setRowSorter(sorter);
        
    }
    
    // Sets the specified sort Direction for the specified Column index
    public void setColDirection (int intCol, SortOrder soIn){
        
        sortKeys.add(new RowSorter.SortKey(intCol, soIn));
        sorter.setSortKeys(sortKeys);
        
        sorter.sort();
    }
    
    public void setCurrentSort (List<RowSorter.SortKey> inputSort){
        sortKeys = inputSort;
    }
    
    public void applyCurrentSort(){
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
}
