import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { authFetch } from '../utils/auth';
import { API_BASE_URL } from '../utils/api';

function TransactionHistory() {
  const { accountId } = useParams();
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchTransactions() {
      const response = await authFetch(`${API_BASE_URL}/api/accounts/${accountId}/transactions`);
      const data = await response.json();
      setTransactions(data);
      setLoading(false);
    }
    fetchTransactions();
  }, [accountId]);

  return (
    <div>
      <h1>Transaction History</h1>
      {loading && <p>Loading...</p>}
      {!loading && transactions.length === 0 && <p>No transactions yet.</p>}
      {!loading && transactions.length > 0 && (
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
                <td>{new Date(txn.date).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      <p>
        <Link to={`/accounts/${accountId}`}>&larr; Back to Account</Link>
      </p>
    </div>
  );
}

export default TransactionHistory;
