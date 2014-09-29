package view;

import java.util.List;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;

/**
 * This class implements the Transaction interface and additionally serves as a compound for all those transactions that are used by the
 * subordinates of the Log class. Calling the methods of this class will result in calls of the same methods of the subordinate
 * Transactions. 
 * 
 * The class is used to implement those methods of the Log class that are dependent on transactions
 * 
 * @author Rafael Theis
 * @see {@link de.uni-saarland.cs.st.pirates.logger}
 */
public class CompTransaction implements Transaction {

	private List<Transaction> transactions;

	public CompTransaction(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	@Override
	public Transaction set(Key arg0, int arg1) throws NullPointerException, IllegalArgumentException {
		for(Transaction trans: transactions)
		{
			if(trans != null)
				trans.set(arg0, arg1);
		}
		
		return this;
	}
	
	public Transaction getTranscation(int index){
		return transactions.get(index);
	}
}
