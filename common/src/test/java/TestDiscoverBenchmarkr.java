import com.github.benchmarkr.executable.BenchmarkrBinaryDiscovery;
import com.github.benchmarkr.executable.BenchmarkrCommands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDiscoverBenchmarkr {
  @Test
  public void testVersion() {
    Assertions.assertEquals(
        BenchmarkrCommands.version(BenchmarkrBinaryDiscovery.benchmarkr()), "0.0.2"
    );
  }

}
