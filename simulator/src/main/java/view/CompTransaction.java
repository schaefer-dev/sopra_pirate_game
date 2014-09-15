package view;

import java.util.List;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Key;
import de.unisaarland.cs.st.pirates.logger.LogWriter.Transaction;

public class CompTransaction implements Transaction {

	private List<Transaction> transactions;

	public CompTransaction(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	@Override
	public Transaction set(Key arg0, int arg1) throws NullPointerException, IllegalArgumentException {
		for(Transaction trans: transactions)
			trans.set(arg0, arg1);
		
		return this;
	}
	
	public Transaction getTranscation(int index){
		return transactions.get(index);
	}
}
