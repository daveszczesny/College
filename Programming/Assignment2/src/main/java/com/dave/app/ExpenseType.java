/*
 * 
 * @Author: Dawid Szczesny
 * @ID: 21300293
 */

package main.java.com.dave.app;

/*
 * 
 * Enum class, with a type member, allowing for cleaner print
 */
public enum ExpenseType {
    EQUIPMENT("EQUIPMENT"),
    SUPPLIES("SUPPLIES"),
    ENTERTAINMENT("ENTERTAINMENT"),
    OTHER("OTHER"),
    TRAVEL_AND_SUBSISTENCE("TRAVEL AND SUBSISTENCE");

    private final String type;

    /**
     * @param type
     */
    ExpenseType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return type
     */
    public String type() {
        return type;
    }

}
