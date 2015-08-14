package hugeinteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HugeIntegerApi {
	@Autowired
	HugeIntegerOperations operation;

	public HugeIntegerApi() {}

	@RequestMapping("add")
	public String add(@RequestParam("Number1") String number1, @RequestParam("addToNumber2") String number2) {
		return operation.add(number1, number2);
	}

	@RequestMapping(value = "subtract")
	public String sub(@RequestParam("Number1") String number1,@RequestParam("subFromNumber2") String number2) {
		return operation.subtract(number1, number2);
	}

	@RequestMapping("isZero")
	public boolean isZero(@RequestParam("number") String number) {
		return operation.equals(number);
	}

	@RequestMapping("relation")
	public boolean relations(@RequestParam(value = "Number1") String number1,@RequestParam("relation") String relation, @RequestParam(value = "Number2", defaultValue = "") String number2) {
		return operation.relationalOperations(number1, number2, relation);
	}
}
