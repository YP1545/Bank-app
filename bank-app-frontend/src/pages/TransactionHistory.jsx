import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

function TransactionHistory() {
  const { accountId } = useParams();
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    async function fetchTransactions() {
      const response = await fetch(`http://localhost:8080/api/accounts/${accountId}/transactions`);
      const data = await response.json();
      setTransactions(data);
    }
    fetchTransactions();
  }, [accountId]);

  return (
    <div>
      <h1>Transaction History</h1>
      <table>
        <thead>
          <tr>
            <th>Transaction ID</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map((txn) => (
            <tr key={txn.id}>
              <td>{txn.id}</td>
              <td>{txn.type}</td>
              <td>{txn.amount}</td>
              <td>{JSON.stringify(txn.date)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TransactionHistory;