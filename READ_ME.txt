There is need for an application to find a quote from market of lenders for 36-month loans that apply interest on a monthly basis.
Each lender in the market offers an amount of money to lend and the annual interest rate they expect in return. 
A list of all lenders and their offers will be provided in a CSV file.
The application should provide as low a rate as is possible to ensure that quotes are as competitive as they can be against our competitors'. 
Details of the monthly repayment amount and the total repayment amounts should be shown in addition to the amount requested and the
annual interest rate for the quote.
Repayment amounts should be displayed to two decimal places and the annual interest rate displayed to one decimal place.
A quote may be requested in any £100 increment between £1000 and £15000 inclusive. 
If the market does not have enough offers to fulfil the request, then the application should state that it is
not possible to provide a quote.

The application should take arguments in the form:
[market_file_path] [loan_amount]
And produce outputs in the form:
Requested amount: £XXXX
Annual Interest Rate: X.X%
Monthly repayment: £XXXX.XX
Total repayment: £XXXX.XX
Example output:
> Requested amount: £1000
> Annual Interest Rate: 7.0%
> Monthly repayment: £30.78
> Total repayment: £1108.10

=============================================================================================

To execute this program:
1- change directory to: CodeExample folder (the folder containing POM.XML)
2- run command prompt pointing to the above directory and execute the following commands to run the program:
        mvn clean install
	mvn exec:java -Dexec.mainClass=loan.quote.LoanQuoteApp -Dexec.args="PATH_TO_CSV_FILE LOAN_AMOUNT"
for example: mvn exec:java -Dexec.mainClass=loan.quote.LoanQuoteApp -Dexec.args="C:\Users\Home\Example.csv 1000"
3- To run unit tests execute the following commands:
	mvn -Dtest=InputValidatorTest test
	mvn -Dtest=LenderRecordValidatorTest test
	mvn -Dtest=LoanQuoteServiceTest test