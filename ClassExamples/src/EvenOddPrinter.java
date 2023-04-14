public class EvenOddPrinter {
    private int limit;
    private int count;
    private boolean isOdd;

    public EvenOddPrinter(int limit) {
        this.limit = limit;
        this.count = 1;
        this.isOdd = true;
    }

    public synchronized void printOdd() {
        while (count <= limit) {
            if (isOdd) {
                System.out.println("Odd: " + count);
                count++;
                isOdd = false;
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void printEven() {
        while (count <= limit) {
            if (!isOdd) {
                System.out.println("Even: " + count);
                count++;
                isOdd = true;
                notify();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int limit = 50;
        EvenOddPrinter printer = new EvenOddPrinter(limit);

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                printer.printOdd();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                printer.printEven();
            }
        });

        t1.start();
        t2.start();
        
        /*
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
}