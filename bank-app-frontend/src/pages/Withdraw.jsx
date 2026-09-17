import { useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { authFetch } from '../utils/auth';
import { API_BASE_URL } from '../utils/api';

function Withdraw() {
  const { accountId } = useParams();
  const [amount, setAmount] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  async function handleSubmit(event) {
    event.preventDefault();
    setError('');

    const response = await authFetch(`${API_BASE_URL}/api/accounts/${accountId}/withdraw`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ amount: Number(amount) }),
    });

    if (!response.ok) {
      const errorData = await response.json();
      setError(errorData.error);
      return;
    }

    navigate(`/accounts/${accountId}`);
  }

  return (
    <div>
      <h1>Withdraw</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="number"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          required
        />
        <button type="submit">Submit</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <p>
        <Link to={`/accounts/${accountId}`}>&larr; Back to Account</Link>
      </p>
    </div>
  );
}

export default Withdraw;
