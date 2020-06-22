package gerador.comandos;

import gerador.util.Percentil;
import gerador.util.PercentilException;

import java.util.Calendar;
import java.util.Locale;

import junit.framework.TestCase;

public class PercentilTest extends TestCase {

	public void testPercentil() throws PercentilException{
		Percentil pm = new Percentil(5.81,38.13,0,5.63,0,0,0,7.67,3.68,7,
				0,0,0,0,4.96,0,0,0,0,0,0,0,0,0,35.31,2.75,8.88,0,24.41,2.48,9.22);
		assertEquals(9.22, pm.percentil(0.9));
		assertEquals(0.0,pm.percentil(0.5));
		assertEquals(0.0,pm.percentil(0.4));
		assertEquals(2.48,pm.percentil(0.6));
		assertEquals(4.96,pm.percentil(0.7));

		pm = new Percentil(6.26,0.00,8.37,0.00,0.00,4.72,0.00,0.00,51.67,15.76,0.00,10.54,0.00,
				0.00,0.00,4.50,10.38,0.00,0.00,0.00,0.00,0.00,0.00,3.21,9.43,0.00,0.00,0.00,3.59,0.00,14.37);
		assertEquals(10.54, pm.percentil(0.9));
		assertEquals(0.0,pm.percentil(0.5));
		assertEquals(0.0,pm.percentil(0.40));
		assertEquals(0.0,pm.percentil(0.60));
		assertEquals(4.50,pm.percentil(0.70));

		pm = new Percentil(52.0,55.9,56.7,59.4,60.2,54.4,55.9,56.8,59.4,60.3,54.5,56.2,
				57.2,59.5,60.5,55.7,56.4,57.6,59.8,60.6,55.8,56.4,58.9,60.0,60.8);
		assertEquals(57.2, pm.percentil(0.50));
		assertEquals(55.9, pm.percentil(0.25));
		assertEquals(55.85, pm.percentil(0.20));
		
		Calendar c = Calendar.getInstance(Locale.US);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		assertEquals(31, Calendar.DAY_OF_MONTH);
	}
}
