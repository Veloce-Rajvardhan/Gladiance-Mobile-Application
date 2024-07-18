package com.gladiance.ui.models;

import java.util.List;

public class RoomServiceRequest {

    private String Ref;
    private String GAAProjectSpaceRef;
    private List<LineItem> LineItems;

    public String getRef() {
        return Ref;
    }

    public void setRef(String ref) {
        Ref = ref;
    }

    public String getGAAProjectSpaceRef() {
        return GAAProjectSpaceRef;
    }

    public void setGAAProjectSpaceRef(String GAAProjectSpaceRef) {
        this.GAAProjectSpaceRef = GAAProjectSpaceRef;
    }

    public List<LineItem> getLineItems() {
        return LineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        LineItems = lineItems;
    }

    public static class LineItem {
        private String Ref;
        private String GAAProjectSpaceInRoomDiningRequestRef;
        private String RBItemRef;
        private double Quantity;

        public String getRef() {
            return Ref;
        }

        public void setRef(String ref) {
            Ref = ref;
        }

        public String getGAAProjectSpaceInRoomDiningRequestRef() {
            return GAAProjectSpaceInRoomDiningRequestRef;
        }

        public void setGAAProjectSpaceInRoomDiningRequestRef(String GAAProjectSpaceInRoomDiningRequestRef) {
            this.GAAProjectSpaceInRoomDiningRequestRef = GAAProjectSpaceInRoomDiningRequestRef;
        }

        public String getRBItemRef() {
            return RBItemRef;
        }

        public void setRBItemRef(String RBItemRef) {
            this.RBItemRef = RBItemRef;
        }

        public double getQuantity(double quantity) {
            return Quantity;
        }

        public void setQuantity(double quantity) {
            Quantity = quantity;
        }
    }
}
