package hugeinteger;

import java.util.Arrays;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

@Service
public class OperationExecution implements HugeIntegerOperations {

	private static final String IS_LESS_THAN_OR_EQUAL_TO = "isLessThanOrEqualTo";
	private static final String IS_GREATER_THAN_OR_EQUAL_TO = "isGreaterThanOrEqualTo";
	private static final String IS_LESS_THAN = "isLessThan";
	private static final String IS_GREATER_THAN = "isGreaterThan";
	private static final String IS_NOT_EQUAL_TO = "isNotEqualTo";
	private static final String IS_EQUAL_TO = "isEqualTo";

	public OperationExecution() {}

	@Override
	public String add(String number1, String number2) {
		HugeInteger firstNumber = new HugeInteger(number1);
		HugeInteger secondNumber = new HugeInteger(number2);

		int maxLength = findMaximum(firstNumber.getLength(), secondNumber.getLength());
		HugeInteger result = new HugeInteger(maxLength + 1);
		
		if (firstNumber.isLessThan(secondNumber)) {
			HugeInteger tempNumber = secondNumber;
			secondNumber = firstNumber;
			firstNumber = tempNumber;
		}
		
		ArrayUtils.reverse(firstNumber.getNumber());
		ArrayUtils.reverse(secondNumber.getNumber());
		
		for (int index = 0; index < findMinimum(firstNumber.getLength(), secondNumber.getLength()); index++) {
			result.getNumber()[index] += firstNumber.getNumber()[index] + secondNumber.getNumber()[index];
			if (result.getNumber()[index] > 9) {
				result.getNumber()[index + 1] += result.getNumber()[index] / 10;
				result.getNumber()[index] = result.getNumber()[index] % 10;
			}
		}
		
		ArrayUtils.reverse(result.getNumber());
		return result.toString();

	}

	@Override
	public String subtract(String number1, String number2) {
		HugeInteger firstNumber = new HugeInteger(number1);
		HugeInteger secondNumber = new HugeInteger(number2);
		boolean isNegative = false;
		ArrayUtils.reverse(firstNumber.getNumber());
		ArrayUtils.reverse(secondNumber.getNumber());

		if(!isNegativeNumber(number1) && !isNegativeNumber(number2)) {
			if (firstNumber.isLessThan(secondNumber)) {
				HugeInteger tempNumber = secondNumber;
				secondNumber = firstNumber;
				firstNumber = tempNumber;
				isNegative = true;
			}

			for (int i = 0; i < findMinimum(firstNumber.getLength(), secondNumber.getLength()); i++) {
				firstNumber.getNumber()[i] -= secondNumber.getNumber()[i];
				if (firstNumber.getNumber()[i] < 0) {
					firstNumber.getNumber()[i] += 10;
					firstNumber.getNumber()[i + 1] -= 1;
				}
			}
			ArrayUtils.reverse(firstNumber.getNumber());

			return isNegative ? "-" + firstNumber.toString() : firstNumber.toString();
		} else if (isNegativeNumber(number1) && !isNegativeNumber(number2)) {
			return "-" + add(number1.substring(1), number2);
		} else if (!isNegativeNumber(number1) && isNegativeNumber(number2)) {
			return add(number1, number2.substring(1));
		}
	}

	@Override
	public boolean isZero(String number1) {
		HugeInteger number = new HugeInteger(number1);
		return Arrays.asList(ArrayUtils.toObject(number.getNumber())).stream().map(digit -> digit == 0).reduce(true,(a, b) -> a && b);
	}
	
	public boolean isNegativeNumber(String number) {
		return number.charAt(0) == '-';
	}

	@Override
	public boolean relationalOperations(String number1, String number2, String operator) {

		HugeInteger firstNumber = new HugeInteger(number1);
		HugeInteger secondNumber = new HugeInteger(number2);

		Predicate<HugeInteger> isEqualTo = b -> {
			return firstNumber.isEqualTo(b);
		};
		Predicate<HugeInteger> isNotEqualTo = b -> {
			return firstNumber.isNotEqualTo(b);
		};
		Predicate<HugeInteger> isGreaterThan = b -> {
			return firstNumber.isGreaterThan(b);
		};
		Predicate<HugeInteger> isLessThan = b -> {
			return firstNumber.isLessThan(b);
		};
		Predicate<HugeInteger> isGreaterThanOrEqualTo = b -> {
			return firstNumber.isGreaterThanOrEqualTo(b);
		};
		Predicate<HugeInteger> isLessThanOrEqualTo = b -> {
			return firstNumber.isLessThanOrEqualTo(b);
		};

		switch (operator) {

		case IS_EQUAL_TO:
			return isEqualTo.test(secondNumber);
		case IS_NOT_EQUAL_TO:
			return isNotEqualTo.test(secondNumber);
		case IS_GREATER_THAN:
			return isGreaterThan.test(secondNumber);
		case IS_LESS_THAN:
			return isLessThan.test(secondNumber);
		case IS_GREATER_THAN_OR_EQUAL_TO:
			return isGreaterThanOrEqualTo.test(secondNumber);
		case IS_LESS_THAN_OR_EQUAL_TO:
			return isLessThanOrEqualTo.test(secondNumber);
		}
		return true;
	}

	private int findMinimum(Integer a, Integer b) {
		return (a < b) ? a : b;
	}

	private int findMaximum(Integer a, Integer b) {
		return (a > b) ? a : b;
	}
}
