import java.util.concurrent.*;

public class ForkJoin {
  public static void main(String... args) {
    ForkJoinPool common = ForkJoinPool.commonPool();
    Phaser phaser = new Phaser(200);
    common.invoke(new PhaserWaiter(phaser));
  }

  private static class PhaserWaiter extends RecursiveAction {
    private final Phaser phaser;

    private PhaserWaiter(Phaser phaser) {
      this.phaser = phaser;
      System.out.println("n=" + ForkJoinPool.commonPool().getPoolSize());
    }

    protected void compute() {
      if (phaser.getPhase() > 0) return; // we've passed first phase
      PhaserWaiter p1 = new PhaserWaiter(phaser);
      p1.fork();
      phaser.arriveAndAwaitAdvance();
      p1.join();
    }
  }
}
  
