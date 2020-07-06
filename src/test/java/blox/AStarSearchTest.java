package blox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import astar.AbstractAStarSearcher;
import junit.framework.TestCase;
import astar.Move;
import blox.BloxAStarSearcher;
import blox.Scape;
import blox.Site;

public class AStarSearchTest extends TestCase {

	Scape scape = null;
	boolean assertPath = true;
	private boolean searchTwice = false;

	protected void setUp() throws Exception {
		super.setUp();
		scape = new Scape();
	}

	public void testStartIsTarget() throws IOException {
		Scape scape = new Scape();
		scape.load("level1.txt");
		List<Input> inputs = Arrays.asList();
		doTest(scape, scape.start, scape.start, inputs, false);
	}

	public void testTargetNotReachable() throws IOException {
		List<Input> inputs = Arrays.asList();
		doTest("notreachable.txt", inputs);
	}

	public void testTrivial() throws IOException {
		doTest("trivial.txt", 2);
	}

	public void testSimpleToggle() throws IOException {
		List<Input> inputs = Arrays.asList(Input.left, Input.right, Input.left,
				Input.left, Input.left, Input.left);
		doTest("testsimpletoggle.txt", inputs);
	}

	public void testLevel0() throws IOException {
		doTest("level0.txt", 18);
	}

	public void testProveOptimalForTeleport_SingleTeleport() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.up, Input.left,
				Input.down);
		doTest("proveOptimalForTeleport1.txt", inputs);
	}

	public void testProveOptimalForTeleport_MultipleTeleport()
			throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.up, Input.left,
				Input.right, Input.down, Input.left, Input.down);
		doTest("proveOptimalForMultipleTeleports.txt", inputs);
	}

	public void testSimpleTeleport() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.down, Input.left,
				Input.up, Input.right, Input.down, Input.left, Input.up,
				Input.nextBlock, Input.down, Input.right);
		doTest("testsimpleteleport.txt", inputs);
	}

	public void testSimpleTeleport2() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.down, Input.left,
				Input.up, Input.right, Input.down, Input.left, Input.down,
				Input.down, Input.right, Input.nextBlock, Input.left, Input.up,
				Input.up, Input.right);
		doTest("testsimpleteleport2.txt", inputs, true);
	}

	public void testLevel1() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right,
				Input.down, Input.right, Input.right, Input.right, Input.down);
		doTest("level1.txt", inputs);
	}

	public void testLevel2() throws IOException {
		List<Input> inputs = Arrays.asList(Input.up, Input.right, Input.down,
				Input.right, Input.right, Input.right, Input.right, Input.up,
				Input.up, Input.down, Input.right, Input.down, Input.right,
				Input.right, Input.up, Input.right, Input.up);
		doTest("level2.txt", inputs);
	}

	public void testLevel3() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.up, Input.right,
				Input.right, Input.right, Input.up, Input.left, Input.down,
				Input.right, Input.up, Input.up, Input.right, Input.right,
				Input.right, Input.down, Input.down, Input.down, Input.right,
				Input.up);
		doTest("level3.txt", inputs);
	}

	public void testLevel4() throws IOException {
		List<Input> inputs = Arrays.asList(Input.up, Input.left, Input.up,
				Input.right, Input.right, Input.up, Input.right, Input.right,
				Input.right, Input.right, Input.right, Input.right, Input.down,
				Input.right, Input.down, Input.down, Input.down, Input.down,
				Input.down, Input.right, Input.up, Input.left, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.down);
		doTest("level4.txt", inputs);
	}

	public void testLevel5() throws IOException {
		List<Input> inputs = Arrays.asList(Input.left, Input.left, Input.left,
				Input.right, Input.left, Input.left, Input.left, Input.left,
				Input.left, Input.down, Input.right, Input.down, Input.down,
				Input.right, Input.right, Input.right, Input.right, Input.down,
				Input.right, Input.right, Input.right, Input.right, Input.left,
				Input.left, Input.left, Input.left, Input.down, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.left);
		doTest("level5.txt", inputs);
	}

	/**
	 * @throws IOException
	 */
	public void testLevel6() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right,
				Input.right, Input.down, Input.down, Input.right, Input.down,
				Input.down, Input.right, Input.down, Input.right, Input.up,
				Input.left, Input.left, Input.left, Input.up, Input.up,
				Input.left, Input.up, Input.up, Input.up, Input.right,
				Input.right, Input.down, Input.right, Input.right, Input.up,
				Input.left, Input.down, Input.down, Input.right, Input.right,
				Input.down, Input.down, Input.right);
		// List<Input> inputs = Arrays.asList(Input.right, Input.right,
		// Input.right, Input.down, Input.right, Input.down, Input.down,
		// Input.right, Input.down, Input.down, Input.right, Input.up,
		// Input.left, Input.left, Input.left, Input.up, Input.up,
		// Input.left, Input.up, Input.up, Input.up, Input.right,
		// Input.right, Input.right, Input.down, Input.down, Input.left,
		// Input.up, Input.right, Input.right, Input.down, Input.right,
		// Input.down, Input.down, Input.right);
		doTest("level6.txt", inputs);
	}

	/**
	 * single strong switch
	 */
	public void testLevel7() throws IOException {
		List<Input> inputs = Arrays.asList(Input.down, Input.left, Input.up,
				Input.right, Input.right, Input.right, Input.right,
				Input.right, Input.down, Input.right, Input.left, Input.up,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.down, Input.right, Input.down, Input.right, Input.down,
				Input.right, Input.right, Input.right, Input.up, Input.up,
				Input.right, Input.down, Input.left, Input.up, Input.right,
				Input.up, Input.up, Input.right, Input.right, Input.right,
				Input.down, Input.right, Input.down, Input.right, Input.down,
				Input.left, Input.up);
		doTest("level7.txt", inputs);
	}

	// single teleport
	public void testLevel8() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right,
				Input.down, Input.nextBlock, Input.up, Input.up, Input.up,
				Input.up, Input.down, Input.right, Input.right);

		doTest("level8.txt", inputs, true);
	}

	public void testLevel9() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.down,
				Input.right, Input.right, Input.right, Input.right,
				Input.right, Input.right, Input.up, Input.right, Input.left,
				Input.down, Input.left, Input.left, Input.left, Input.left,
				Input.up, Input.nextBlock, Input.right, Input.down,
				Input.right, Input.right, Input.right, Input.right, Input.down);
		doTest("level9.txt", inputs);
	}

	public void testLevel10() throws IOException {
		List<Input> inputs = Arrays.asList();

		inputs = Arrays.asList(Input.right, Input.right, Input.left,
				Input.down, Input.down, Input.down, Input.right, Input.down,
				Input.down, Input.down, Input.down, Input.down, Input.left,
				Input.left, Input.left, Input.left, Input.up, Input.left,
				Input.left, Input.left, Input.down, Input.up, Input.right,
				Input.right, Input.right, Input.down, Input.right, Input.right,
				Input.right, Input.up, Input.nextBlock, Input.right,
				Input.down, Input.right, Input.down, Input.down, Input.right,
				Input.down, Input.down, Input.down, Input.left, Input.down,
				Input.up, Input.right, Input.up, Input.up, Input.up, Input.up,
				Input.left, Input.left, Input.up, Input.left, Input.down,
				Input.left, Input.left, Input.left, Input.left, Input.left);
		doTest("level10.txt", inputs);
	}

	public void testLevel11() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right,
				Input.right, Input.right, Input.up, Input.left, Input.down,
				Input.down, Input.down, Input.right, Input.right, Input.right,
				Input.down, Input.left, Input.up, Input.left, Input.left,
				Input.left, Input.up, Input.up, Input.right, Input.up,
				Input.right, Input.right, Input.down, Input.right, Input.up,
				Input.left, Input.left, Input.left, Input.down, Input.down,
				Input.left, Input.left, Input.left, Input.up, Input.up,
				Input.right, Input.up, Input.up, Input.left, Input.down,
				Input.right, Input.up, Input.right, Input.down, Input.left);
		doTest("level11.txt", inputs);
	}

	public void testLevel12() throws IOException {
		List<Input> inputs = Arrays.asList(Input.left, Input.down, Input.right,
				Input.up, Input.right, Input.up, Input.right, Input.up,
				Input.left, Input.down, Input.right, Input.up, Input.right,
				Input.up, Input.right, Input.right, Input.right, Input.down,
				Input.down, Input.down, Input.left, Input.up, Input.right,
				Input.up, Input.up, Input.up, Input.left, Input.left,
				Input.left, Input.right, Input.right, Input.right, Input.down,
				Input.down, Input.down, Input.left, Input.down, Input.right,
				Input.up, Input.right, Input.down, Input.left, Input.up,
				Input.right, Input.up, Input.up, Input.right, Input.up,
				Input.down, Input.left, Input.down, Input.down, Input.down,
				Input.left, Input.up, Input.right, Input.up, Input.left,
				Input.up, Input.left, Input.left, Input.left, Input.down,
				Input.down, Input.left);
		doTest("level12.txt", inputs);
	}

	public void testLevel13() throws IOException {
		List<Input> inputs = Arrays.asList(Input.up, Input.up, Input.left,
				Input.down, Input.right, Input.down, Input.right, Input.down,
				Input.left, Input.up, Input.up, Input.up, Input.up, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.left, Input.down, Input.down, Input.down, Input.right,
				Input.up, Input.left, Input.down, Input.right, Input.down,
				Input.down, Input.right, Input.down, Input.down, Input.right,
				Input.up, Input.right, Input.right, Input.right, Input.right,
				Input.right, Input.up, Input.left, Input.down, Input.left,
				Input.up, Input.up);
		doTest("level13.txt", inputs);
	}

	public void testLevel14() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right,
				Input.right, Input.right, Input.up, Input.left, Input.left,
				Input.down, Input.right, Input.right, Input.right, Input.down,
				Input.down, Input.down, Input.down, Input.left, Input.left,
				Input.left, Input.down, Input.right, Input.up, Input.right,
				Input.right, Input.up, Input.up, Input.down, Input.right,
				Input.down, Input.left, Input.left, Input.down, Input.down,
				Input.right, Input.right, Input.left, Input.left, Input.up,
				Input.left, Input.left, Input.up, Input.right, Input.right,
				Input.right, Input.up, Input.up, Input.up, Input.up,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.left, Input.up, Input.right, Input.down, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.down,
				Input.down, Input.down, Input.right, Input.down, Input.right);
		doTest("level14.txt", inputs);
	}

	public void testLevel15() throws IOException {
		List<Input> inputs = Arrays
				.asList(Input.right, Input.right, Input.right, Input.right,
						Input.up, Input.up, Input.nextBlock, Input.up,
						Input.up, Input.up, Input.up, Input.up, Input.right,
						Input.right, Input.right, Input.up, Input.up,
						Input.right, Input.right, Input.right, Input.right,
						Input.down, Input.down, Input.up, Input.left, Input.up,
						Input.nextBlock, Input.left, Input.left, Input.left,
						Input.left, Input.down, Input.left, Input.down,
						Input.up, Input.nextBlock, Input.left, Input.left,
						Input.left, Input.down, Input.nextBlock, Input.up,
						Input.left, Input.left, Input.left, Input.left,
						Input.down, Input.left, Input.left, Input.down,
						Input.down, Input.down, Input.left, Input.down,
						Input.right, Input.up, Input.right, Input.right,
						Input.right, Input.right, Input.right, Input.right,
						Input.right);
		doTest("level15.txt", inputs, true);
	}

	public void testLevel16() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right,
				Input.right, Input.right, Input.nextBlock, Input.right,
				Input.down, Input.nextBlock, Input.right, Input.up,
				Input.nextBlock, Input.right, Input.left, Input.left,
				Input.left, Input.right, Input.right, Input.right, Input.right,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.down, Input.nextBlock, Input.right, Input.up,
				Input.nextBlock, Input.right, Input.right, Input.right,
				Input.right);
		doTest("level16.txt", inputs, false);
	}

	public void testLevel17() throws IOException {
		List<Input> inputs = Arrays.asList(Input.down, Input.down, Input.down,
				Input.down, Input.down, Input.left, Input.up, Input.right,
				Input.right, Input.right, Input.right, Input.right, Input.up,
				Input.right, Input.right, Input.right, Input.left, Input.left,
				Input.left, Input.down, Input.left, Input.left, Input.left,
				Input.left, Input.left, Input.up, Input.up, Input.up, Input.up,
				Input.right, Input.right, Input.right, Input.right,
				Input.right, Input.down, Input.right, Input.right, Input.right,
				Input.right, Input.down, Input.left, Input.up, Input.right,
				Input.down, Input.up, Input.right, Input.down, Input.left,
				Input.up, Input.up, Input.right, Input.down, Input.left,
				Input.left, Input.up, Input.right, Input.down, Input.left,
				Input.left, Input.left, Input.left, Input.up, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.down,
				Input.down, Input.down, Input.down, Input.right, Input.right,
				Input.right, Input.right, Input.right, Input.up, Input.right,
				Input.right, Input.right, Input.down, Input.down, Input.up,
				Input.left, Input.up, Input.left, Input.left, Input.left,
				Input.down, Input.left, Input.left, Input.left, Input.up,
				Input.left, Input.up, Input.up, Input.up, Input.right,
				Input.right, Input.right, Input.right, Input.right, Input.down,
				Input.right, Input.right, Input.right);
		doTest("level17.txt", inputs, true);
	}

	public void testLevel18() throws IOException {
		List<Input> inputs = Arrays.asList(Input.up, Input.left, Input.down,
				Input.right, Input.right, Input.right, Input.right, Input.up,
				Input.up, Input.down, Input.down, Input.left, Input.left,
				Input.left, Input.up, Input.right, Input.down, Input.left,
				Input.left, Input.up, Input.right, Input.down, Input.right,
				Input.right, Input.right, Input.down, Input.down, Input.up,
				Input.up, Input.left, Input.left, Input.left, Input.up,
				Input.left, Input.down, Input.right, Input.up, Input.left,
				Input.left, Input.down, Input.down, Input.down, Input.down,
				Input.right, Input.right, Input.left, Input.left, Input.up,
				Input.up, Input.up, Input.up, Input.right, Input.right,
				Input.down, Input.left, Input.up, Input.right, Input.down,
				Input.down, Input.left, Input.up, Input.right, Input.down,
				Input.right, Input.up, Input.right, Input.right, Input.right,
				Input.right, Input.right, Input.down, Input.down, Input.down,
				Input.right, Input.up, Input.left, Input.down, Input.down,
				Input.right, Input.up, Input.left, Input.down, Input.left,
				Input.up, Input.right);
		doTest("level18.txt", inputs);
	}

	public void testLevel19() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right,
				Input.right, Input.right, Input.right, Input.right,
				Input.right, Input.right, Input.down, Input.right, Input.up,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.left, Input.down, Input.right, Input.up, Input.right,
				Input.right, Input.right, Input.right, Input.right, Input.down,
				Input.down, Input.down, Input.down, Input.down, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.down,
				Input.left, Input.up, Input.right, Input.right, Input.right,
				Input.right, Input.right, Input.right, Input.up, Input.left,
				Input.down, Input.left, Input.left, Input.left, Input.left,
				Input.left, Input.down, Input.down, Input.down, Input.down,
				Input.right, Input.right, Input.right, Input.left, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.up,
				Input.up);
		doTest("level19.txt", inputs);
	}

	public void testLevel20() throws IOException {
		List<Input> inputs = Arrays.asList(Input.down, Input.left, Input.down,
				Input.right, Input.down, Input.left, Input.up, Input.up,
				Input.right, Input.up, Input.up, Input.left, Input.left,
				Input.left, Input.left, Input.down, Input.down, Input.right,
				Input.up, Input.left, Input.left, Input.down, Input.down,
				Input.down, Input.left, Input.down, Input.up, Input.right,
				Input.up, Input.up, Input.right, Input.up, Input.up,
				Input.right, Input.right, Input.right, Input.down, Input.left,
				Input.down, Input.left, Input.left, Input.left, Input.left,
				Input.down, Input.down, Input.down, Input.nextBlock, Input.up,
				Input.left, Input.down, Input.nextBlock, Input.down,
				Input.down, Input.right, Input.right, Input.right, Input.right,
				Input.down);
		doTest("level20.txt", inputs, true);
	}

	public void testLevel21() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.down, Input.left,
				Input.up, Input.left, Input.down, Input.right, Input.up,
				Input.right, Input.right, Input.up, Input.right, Input.right,
				Input.right, Input.up, Input.left, Input.down, Input.down,
				Input.down, Input.up, Input.up, Input.up, Input.right,
				Input.down, Input.left, Input.left, Input.left, Input.down,
				Input.left, Input.left, Input.down, Input.left, Input.up,
				Input.right, Input.down, Input.right, Input.up, Input.left,
				Input.down, Input.right, Input.up, Input.left, Input.down,
				Input.right, Input.down, Input.down, Input.right, Input.down,
				Input.down, Input.right, Input.right, Input.right, Input.up,
				Input.right, Input.down, Input.left, Input.left, Input.left,
				Input.left, Input.up, Input.right, Input.down, Input.right,
				Input.right, Input.right, Input.up, Input.up, Input.up,
				Input.up, Input.right, Input.right, Input.right);

		doTest("level21.txt", inputs);
	}

	public void testLevel22() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right, Input.up,
				Input.right, Input.right, Input.right, Input.down, Input.right,
				Input.right, Input.down, Input.down, Input.down, Input.left,
				Input.down, Input.up, Input.right, Input.up, Input.up,
				Input.up, Input.left, Input.up, Input.right, Input.down,
				Input.left, Input.up, Input.left, Input.up, Input.left,
				Input.left, Input.down, Input.left, Input.left, Input.left,
				Input.down, Input.right, Input.up, Input.right, Input.down,
				Input.left, Input.down, Input.down, Input.down, Input.right,
				Input.down, Input.up, Input.left, Input.up, Input.up, Input.up,
				Input.right, Input.up, Input.left, Input.down, Input.right,
				Input.up, Input.right, Input.up, Input.right, Input.right,
				Input.right, Input.down, Input.right, Input.right, Input.right,
				Input.right, Input.up);

		inputs = Arrays.asList(Input.right, Input.up, Input.right, Input.right,
				Input.right, Input.right, Input.down, Input.right, Input.down,
				Input.down, Input.down, Input.left, Input.down, Input.up,
				Input.right, Input.up, Input.up, Input.up, Input.left,
				Input.up, Input.right, Input.down, Input.left, Input.up,
				Input.left, Input.up, Input.left, Input.left, Input.down,
				Input.left, Input.left, Input.left, Input.down, Input.right,
				Input.up, Input.right, Input.down, Input.left, Input.down,
				Input.down, Input.down, Input.right, Input.down, Input.up,
				Input.left, Input.up, Input.up, Input.up, Input.right,
				Input.up, Input.left, Input.down, Input.right, Input.up,
				Input.right, Input.up, Input.right, Input.right, Input.right,
				Input.down, Input.right, Input.right, Input.right, Input.right,
				Input.up);
		doTest("level22.txt", inputs, false);
	}

	public void testLevel23() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.down, Input.down,
				Input.right, Input.up, Input.right, Input.right, Input.right,
				Input.right, Input.right, Input.up, Input.left, Input.down,
				Input.right, Input.left, Input.up, Input.left, Input.left,
				Input.up, Input.up, Input.up, Input.up, Input.right,
				Input.right, Input.right, Input.right, Input.up, Input.left,
				Input.down, Input.left, Input.left, Input.left, Input.down,
				Input.down, Input.down, Input.left, Input.down, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.left, Input.left, Input.up, Input.up, Input.up,
				Input.right, Input.right, Input.up, Input.down, Input.left,
				Input.up, Input.right, Input.down, Input.down, Input.right,
				Input.down, Input.down, Input.down, Input.right, Input.right,
				Input.down, Input.right, Input.up, Input.up, Input.up,
				Input.up, Input.up, Input.up, Input.right, Input.down,
				Input.left, Input.up);
		doTest("level23.txt", inputs);
	}

	public void testLevel24() throws IOException {
		List<Input> inputs = Arrays.asList(Input.down, Input.left, Input.down,
				Input.right, Input.down, Input.left, Input.up, Input.up,
				Input.down, Input.down, Input.right, Input.up, Input.left,
				Input.up, Input.right, Input.down, Input.right, Input.right,
				Input.up, Input.up, Input.right, Input.up, Input.left,
				Input.down, Input.right, Input.up, Input.right, Input.right,
				Input.right, Input.right, Input.left, Input.left, Input.left,
				Input.left, Input.left, Input.down, Input.right, Input.left,
				Input.up, Input.right, Input.down, Input.left, Input.up,
				Input.right, Input.right, Input.right, Input.right,
				Input.right, Input.right, Input.right, Input.down, Input.left,
				Input.right, Input.up, Input.right, Input.right, Input.right);
		doTest("level24.txt", inputs);
	}

	public void testLevel25() throws IOException {
		List<Input> inputs = Arrays.asList(Input.up, Input.right, Input.down,
				Input.left, Input.up, Input.right, Input.down, Input.left,
				Input.up, Input.right, Input.right, Input.right, Input.right,
				Input.up, Input.left, Input.up, Input.left, Input.left,
				Input.up, Input.right, Input.left, Input.down, Input.right,
				Input.right, Input.down, Input.right, Input.down, Input.left,
				Input.left, Input.left, Input.left, Input.down, Input.right,
				Input.up, Input.right, Input.right, Input.right, Input.up,
				Input.up, Input.right, Input.right, Input.right, Input.up,
				Input.right, Input.down, Input.left, Input.up, Input.up,
				Input.right, Input.down, Input.left, Input.up, Input.right,
				Input.down, Input.left);
		doTest("level25.txt", inputs);
	}

	public void testLevel26() throws IOException {
		List<Input> inputs = Arrays.asList(Input.up, Input.up, Input.left,
				Input.left, Input.left, Input.down, Input.left, Input.left,
				Input.left, Input.down, Input.right, Input.up, Input.right,
				Input.right, Input.up, Input.up, Input.right, Input.right,
				Input.down, Input.right, Input.right, Input.down, Input.right,
				Input.right, Input.up, Input.up, Input.left, Input.left,
				Input.up, Input.left, Input.left, Input.left, Input.left,
				Input.left, Input.down, Input.left, Input.left, Input.left,
				Input.down, Input.left, Input.down, Input.down,
				Input.nextBlock, Input.up, Input.up, Input.up, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.down, Input.left, Input.left, Input.down, Input.down,
				Input.left, Input.down, Input.up, Input.up, Input.right,
				Input.up, Input.right, Input.right, Input.right, Input.up,
				Input.right, Input.right, Input.right, Input.down, Input.right,
				Input.right, Input.up, Input.up, Input.left, Input.left,
				Input.up, Input.left, Input.left, Input.left, Input.down,
				Input.left, Input.down, Input.down, Input.down, Input.right,
				Input.right, Input.down, Input.right, Input.nextBlock,
				Input.up, Input.up, Input.up, Input.left, Input.left,
				Input.left, Input.down, Input.left, Input.down, Input.down,
				Input.down, Input.right, Input.right, Input.down, Input.left);
		doTest("level26.txt", inputs);
	}

	public void testLevel27() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right,
				Input.right, Input.right, Input.right, Input.up, Input.left,
				Input.down, Input.right, Input.up, Input.right, Input.down,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.left, Input.up, Input.right, Input.down, Input.left,
				Input.left, Input.up, Input.right, Input.down, Input.right,
				Input.right, Input.right, Input.right, Input.up, Input.right,
				Input.right, Input.right, Input.right, Input.down, Input.down,
				Input.down, Input.left, Input.down, Input.down, Input.down,
				Input.left, Input.left, Input.left, Input.down, Input.left,
				Input.up, Input.up, Input.up, Input.up, Input.right,
				Input.down, Input.left, Input.left, Input.left, Input.left,
				Input.left, Input.left, Input.left, Input.up, Input.left,
				Input.down, Input.down, Input.right, Input.up, Input.left,
				Input.down, Input.right, Input.up, Input.left);
		doTest("level27.txt", inputs);
	}

	public void testLevel28() throws IOException {
		List<Input> inputs = Arrays.asList(Input.left, Input.down, Input.down,
				Input.down, Input.right, Input.down, Input.left, Input.up,
				Input.right, Input.down, Input.down, Input.right, Input.right,
				Input.down, Input.down, Input.right, Input.right, Input.right,
				Input.up, Input.up, Input.up, Input.left, Input.up, Input.left,
				Input.up, Input.left, Input.up, Input.left, Input.down,
				Input.right, Input.right, Input.down, Input.right, Input.down,
				Input.right, Input.down, Input.right, Input.right, Input.down,
				Input.left, Input.down, Input.left, Input.left, Input.up,
				Input.up, Input.up, Input.left, Input.up, Input.left, Input.up,
				Input.left, Input.left, Input.up, Input.left, Input.left,
				Input.down, Input.left, Input.up, Input.right, Input.right,
				Input.right, Input.down, Input.right, Input.down, Input.right,
				Input.down, Input.right, Input.down, Input.right, Input.left,
				Input.left, Input.left, Input.left, Input.down, Input.down,
				Input.down, Input.left, Input.left, Input.left, Input.left,
				Input.up, Input.left, Input.up, Input.left, Input.left,
				Input.left, Input.down, Input.nextBlock, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.up, Input.left, Input.up, Input.left, Input.left,
				Input.left, Input.up);
		doTest("level28.txt", inputs);
	}

	public void testLevel29() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.up, Input.left,
				Input.down, Input.left, Input.up, Input.right, Input.down,
				Input.right, Input.down, Input.down, Input.right, Input.right,
				Input.left, Input.left, Input.up, Input.up, Input.left,
				Input.up, Input.right, Input.down, Input.left, Input.up,
				Input.left, Input.down, Input.left, Input.up, Input.up,
				Input.left, Input.left, Input.right, Input.right, Input.down,
				Input.down, Input.right, Input.up, Input.right, Input.down,
				Input.right, Input.up, Input.left, Input.down, Input.right,
				Input.up, Input.up, Input.right, Input.right, Input.left,
				Input.left, Input.down, Input.down, Input.down, Input.down,
				Input.down, Input.down, Input.right, Input.right, Input.left,
				Input.left, Input.up, Input.up, Input.up, Input.up, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.right, Input.right, Input.right, Input.right, Input.down,
				Input.left, Input.up, Input.right, Input.right, Input.right,
				Input.right, Input.right, Input.right, Input.left, Input.left,
				Input.left, Input.left, Input.left, Input.up, Input.left,
				Input.down, Input.down, Input.down, Input.down, Input.left,
				Input.left, Input.left, Input.down, Input.right, Input.up,
				Input.left, Input.down, Input.right, Input.up, Input.left);
		doTest("level29.txt", inputs, true);
	}

	public void testLevel30() throws IOException {
		List<Input> inputs = Arrays.asList(Input.down, Input.right, Input.down,
				Input.down, Input.right, Input.right, Input.up, Input.right,
				Input.right, Input.down, Input.right, Input.right, Input.right,
				Input.right, Input.up, Input.down, Input.left, Input.left,
				Input.left, Input.left, Input.up, Input.left, Input.left,
				Input.left, Input.down, Input.down, Input.left, Input.up,
				Input.right, Input.right, Input.right, Input.right,
				Input.right, Input.right, Input.up, Input.up, Input.up,
				Input.right, Input.right, Input.right, Input.up, Input.right,
				Input.down, Input.down, Input.down, Input.left, Input.left,
				Input.down, Input.left, Input.down, Input.right, Input.up,
				Input.up, Input.right, Input.down, Input.left, Input.down,
				Input.left, Input.left, Input.left, Input.left, Input.up,
				Input.left, Input.left, Input.down, Input.left, Input.left,
				Input.up, Input.left, Input.down, Input.left, Input.up,
				Input.right, Input.up, Input.down, Input.left, Input.down,
				Input.right, Input.up, Input.right, Input.down, Input.right,
				Input.right, Input.up, Input.left, Input.down, Input.down,
				Input.left, Input.up, Input.right, Input.right, Input.right,
				Input.right, Input.right, Input.right, Input.right, Input.up,
				Input.left, Input.up, Input.up, Input.up, Input.right,
				Input.right, Input.right, Input.up, Input.right, Input.up,
				Input.left, Input.left, Input.left, Input.left, Input.left,
				Input.down, Input.left);
		doTest("level30.txt", inputs);
	}

	public void testLevel31() throws IOException {
		List<Input> inputs = Arrays.asList(Input.up, Input.left, Input.down,
				Input.left, Input.left, Input.up, Input.up, Input.up, Input.up,
				Input.down, Input.down, Input.down, Input.down, Input.right,
				Input.right, Input.right, Input.up, Input.left, Input.down,
				Input.right, Input.up, Input.up, Input.up, Input.up,
				Input.right, Input.up, Input.left, Input.down, Input.down,
				Input.right, Input.up, Input.left, Input.down, Input.left,
				Input.left, Input.left, Input.down, Input.down, Input.down,
				Input.left, Input.down, Input.down, Input.up, Input.up,
				Input.right, Input.up, Input.up, Input.up, Input.left,
				Input.left, Input.left, Input.left, Input.down, Input.right,
				Input.up, Input.left, Input.down, Input.down, Input.down,
				Input.down, Input.down, Input.left, Input.up, Input.right,
				Input.left, Input.up, Input.right, Input.down, Input.right,
				Input.up, Input.right, Input.right, Input.up, Input.right,
				Input.right, Input.down, Input.right, Input.right, Input.right,
				Input.up, Input.left, Input.down, Input.right, Input.up,
				Input.up, Input.up, Input.up, Input.up, Input.right,
				Input.down, Input.left);
		doTest("level31.txt", inputs);
	}

	public void testLevel32() throws IOException {
		List<Input> inputs = Arrays.asList(Input.up, Input.up, Input.left,
				Input.down, Input.right, Input.up, Input.right, Input.up,
				Input.right, Input.down, Input.left, Input.right, Input.up,
				Input.left, Input.down, Input.left, Input.down, Input.left,
				Input.up, Input.right, Input.down, Input.left, Input.up,
				Input.right, Input.up, Input.right, Input.up, Input.right,
				Input.down, Input.left, Input.left, Input.down, Input.down,
				Input.down, Input.left, Input.down, Input.left, Input.left,
				Input.up, Input.left, Input.down, Input.right, Input.right,
				Input.right, Input.up, Input.up, Input.up, Input.up, Input.up,
				Input.right, Input.up, Input.right, Input.right, Input.up,
				Input.down, Input.left, Input.left, Input.down, Input.left,
				Input.down, Input.down, Input.down, Input.down, Input.down,
				Input.left, Input.left, Input.left, Input.left, Input.up,
				Input.right, Input.left, Input.down, Input.right, Input.right,
				Input.right, Input.right, Input.up, Input.up, Input.up,
				Input.up, Input.up, Input.right, Input.up, Input.right,
				Input.right, Input.up, Input.down, Input.left, Input.left,
				Input.down, Input.left, Input.down, Input.down, Input.down,
				Input.down, Input.down, Input.left, Input.left, Input.left,
				Input.up, Input.right, Input.down, Input.right, Input.right,
				Input.up, Input.right, Input.up, Input.up, Input.up,
				Input.right, Input.right, Input.up, Input.left, Input.down,
				Input.left, Input.down, Input.left, Input.down, Input.right,
				Input.up, Input.left, Input.left, Input.up, Input.left,
				Input.left, Input.left, Input.left, Input.left, Input.down);
		doTest("level32.txt", inputs);
	}

	public void testLevel33() throws IOException {
		List<Input> inputs = Arrays.asList(Input.right, Input.right,
				Input.right, Input.right, Input.up, Input.left, Input.down,
				Input.down, Input.left, Input.up, Input.right, Input.up,
				Input.up, Input.right, Input.right, Input.down, Input.right,
				Input.down, Input.right, Input.up, Input.left, Input.left,
				Input.up, Input.right, Input.down, Input.right, Input.down,
				Input.down, Input.right, Input.down, Input.left, Input.down,
				Input.down, Input.right, Input.right, Input.up, Input.down,
				Input.left, Input.left, Input.up, Input.up, Input.right,
				Input.up, Input.left, Input.up, Input.up, Input.left, Input.up,
				Input.left, Input.down, Input.right, Input.up, Input.left,
				Input.left, Input.left, Input.down, Input.down, Input.down,
				Input.left, Input.down, Input.left, Input.left, Input.down,
				Input.left, Input.up);
		doTest("level33.txt", inputs);
	}

	private List<Input> asInputList(List<Move> moves) {
		List<Input> inputs = new ArrayList<Input>(moves == null ? 0 : moves
				.size());
		if (moves == null) {
			return inputs;
		}
		for (Move move : moves) {
			if (!(move instanceof AbstractAStarSearcher.InitialMove)) {
				inputs.add(((BloxMove) move).input);
			}
		}
		return inputs;
	}

	private void doTest(Scape scape, Coord startCoord, Coord endCoord,
			List<Input> inputs, boolean sizeOnlyOverride) throws IOException {

		int expectedSize = filter(inputs).size();
		List<String> expectedPathAsString = toString(inputs);
		System.out.println(String.format("scape %s, count: %s, moves :%s",
				scape.id, expectedSize, expectedPathAsString));

		Block block = new Block(startCoord, Orientation.z, 1, 2);
		BloxNode start = new BloxNode(scape, block);
		BloxAStarSearcher searcher = new BloxAStarSearcher(start, new Site(
				Orientation.z, endCoord));

		List<Input> searchResult = asInputList(searcher.search());

		List<String> actualPathAsString = toString(searchResult);
		if (!expectedPathAsString.equals(actualPathAsString)) {
			System.out.println(String.format("Actual path was %s %s", filter(searchResult).size(),actualPathAsString));
		}

		for(int i = 0; i < 5; i++) {
			List<Input> asInputList = asInputList(searcher.search1());
			List<String> strings1 = toString(asInputList);
			System.out.println(String.format("	next best was was : count: %s, moves :%s", filter(asInputList).size(), strings1));
		}
		
		assertEquals(expectedSize, filter(searchResult).size());

		if (assertPath && !sizeOnlyOverride) {
			assertEquals(expectedPathAsString, actualPathAsString);
		}

		if (searchTwice) {
			assertEquals(expectedPathAsString, actualPathAsString);
		}
	}

	private List<Input> filter(List<Input> asInputList) {
		List<Input> inputs = new ArrayList<Input>(asInputList.size());
		for (Input input : asInputList) {
			if (input != Input.nextBlock) {
				inputs.add(input);
			}
		}
		return inputs;
	}

	private void doTest(String scapeName, List<Input> inputs, boolean b)
			throws IOException {
		Scape scape = new Scape();
		scape.load(scapeName);
		doTest(scape, scape.start, scape.end, inputs, b);
	}

	private void doTest(String scapeName, List<Input> inputs)
			throws IOException {
		Scape scape = new Scape();
		scape.load(scapeName);
		doTest(scape, scape.start, scape.end, inputs, true);
	}

	private void doTest(String scapeName, int size) throws IOException {
		Scape scape = new Scape();
		scape.load(scapeName);
		Block block = new Block(scape.start, Orientation.z, 1, 2);
		BloxNode start = new BloxNode(scape, block);
		BloxAStarSearcher searcher = new BloxAStarSearcher(start, new Site(
				Orientation.z, scape.end));
		assertEquals(size, searcher.search().size());
	}

	private List<Input> parse(String line) {
		List<Input> inputs = new ArrayList<Input>();
		String[] bits = line.split(",");
		for (String bit : bits) {
			Pattern p = Pattern.compile("\\s*([U|D|L|R|S])\\s*([0-9]*)\\s*");
			Matcher matcher = p.matcher(bit);
			if (!matcher.matches()) {
				throw new RuntimeException(String.format(
						"failed to match '%s'", bit));
			}
			Input input = getInput(matcher.group(1).charAt(0));
			String countString = matcher.group(2);
			int count = countString == null || countString.trim().length() == 0 ? 1
					: Integer.parseInt(countString);
			for (int i = 0; i < count; i++) {
				inputs.add(input);
			}
		}
		return inputs;
	}

	private Input getInput(char c) {
		switch (c) {
		case 'U':
			return Input.up;
		case 'D':
			return Input.down;
		case 'L':
			return Input.left;
		case 'R':
			return Input.right;
		case 'S':
			return Input.nextBlock;
		}
		return null;
	}

	private List<String> toString(List<Input> inputs) {
		List<String> ll = new ArrayList<String>();
		
		if (inputs.isEmpty()) {
			return Collections.emptyList();
		}
		
		int count = 0;
		Input last = inputs.get(0);

		for (Input input : inputs) {
			if (input != last) {
				ll.add(toString(last, count));
				count = 0;
			}
			count++;
			last = input;
		}

		ll.add(toString(last, count));
		return ll;
	}

	private String toString(Input input, int count) {
		String s = toString(input);
		return count == 1 ? s : s + count;
	}

	private String toString(Input input) {
		switch (input) {
		case down:
			return "D";
		case up:
			return "U";
		case left:
			return "L";
		case right:
			return "R";
		case nextBlock:
		default:
			return "S";
		}
	}

}
