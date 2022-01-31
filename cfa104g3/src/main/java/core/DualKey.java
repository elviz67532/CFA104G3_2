package core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DualKey<K1, K2> {
	private K1 k1;
	private K2 k2;
}
