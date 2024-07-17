package com.gladiance.ui.models;

import java.util.List;

public class LaundryRequest {
    private String Ref;
    private String GAAProjectSpaceRef;
    private List<LineItem> LineItems;

    // Getters and setters
    public String getRef() { return Ref; }
    public void setRef(String Ref) { this.Ref = Ref; }

    public String getGAAProjectSpaceRef() { return GAAProjectSpaceRef; }
    public void setGAAProjectSpaceRef(String GAAProjectSpaceRef) { this.GAAProjectSpaceRef = GAAProjectSpaceRef; }

    public List<LineItem> getLineItems() { return LineItems; }
    public void setLineItems(List<LineItem> LineItems) { this.LineItems = LineItems; }

    public static class LineItem {
        private String Ref;
        private String GAAProjectSpaceLaundryRequestRef;
        private String CustomerLaundryItemRef;
        private int LaundryServiceType;
        private double Quantity;

        // Getters and setters
        public String getRef() { return Ref; }
        public void setRef(String Ref) { this.Ref = Ref; }

        public String getGAAProjectSpaceLaundryRequestRef() { return GAAProjectSpaceLaundryRequestRef; }
        public void setGAAProjectSpaceLaundryRequestRef(String GAAProjectSpaceLaundryRequestRef) { this.GAAProjectSpaceLaundryRequestRef = GAAProjectSpaceLaundryRequestRef; }

        public String getCustomerLaundryItemRef() { return CustomerLaundryItemRef; }
        public void setCustomerLaundryItemRef(String CustomerLaundryItemRef) { this.CustomerLaundryItemRef = CustomerLaundryItemRef; }

        public int getLaundryServiceType() { return LaundryServiceType; }
        public void setLaundryServiceType(int LaundryServiceType) { this.LaundryServiceType = LaundryServiceType; }

        public double getQuantity() { return Quantity; }
        public void setQuantity(double Quantity) { this.Quantity = Quantity; }
    }
}



