    package ru.nsu.fitkulin;

    /**
     * class for info about order.
     */
    public class Order {
        private final int id;
        private final int time;
        private OrderStatus status;

        public Order(int id, int time) {
            this.id = id;
            this.time = time;
            this.status = OrderStatus.QUEUE;
        }

        public int getId() {
            return this.id;
        }

        public int getTime() {
            return this.time;
        }

        public String getStatus() {
            return this.status.toString();
        }

        public void setStatus(OrderStatus status) {
            this.status = status;
        }
    }