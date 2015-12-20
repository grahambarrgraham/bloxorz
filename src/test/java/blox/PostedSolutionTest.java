package blox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class PostedSolutionTest extends TestCase {

	Scape scape = null;

	protected void setUp() throws Exception {
		super.setUp();
		scape = new Scape();
	}

	public void test1() throws IOException {
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("postedSolution.txt");

		int gcount = 0;

		BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
		Map<String, List<Input>> lines = new HashMap<String, List<Input>>();
		String levelName = null;
		for (String line = bReader.readLine(); line != null; line = bReader
				.readLine()) {
			if (line.startsWith("level")) {
				levelName = line.trim();
				lines.put(levelName, new ArrayList<Input>());
			} else if (line.contains(",")) {
				List<Input> moves = parse(line);
				lines.put(levelName, moves);
				int size = filter(moves).size();
				gcount += size;
				System.out.println(String.format("%s : %s : %s", levelName,
						size, gcount));
			}
		}
	}

	private List<Input> parse(String line) {
		List<Input> inputs = new ArrayList<Input>();
		String[] bits = line.split(",");
		for (String bit : bits) {
			Pattern p = Pattern.compile("\\s*([U|D|L|R|S])\\s*([0-9]*)\\s*");
			Matcher matcher = p.matcher(bit);
			if (!matcher.matches()) {
				throw new RuntimeException(String.format("failed to match '%s'",bit));
			}
			Input input = getInput(matcher.group(1).charAt(0));
			String countString = matcher.group(2);
			int count = countString == null || countString.trim().length() == 0? 1 : Integer.parseInt(countString);
			for (int i = 0; i < count; i++) {
				inputs.add(input);
			}
		}
		return inputs;
	}

    private List<Input> filter(List<Input> asInputList) {
        List<Input> inputs = new ArrayList<Input>(asInputList.size());
        for (Input input : asInputList) {
            if( input != Input.nextBlock ) {
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
}
