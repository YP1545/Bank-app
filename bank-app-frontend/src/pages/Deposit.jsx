import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function Deposit() {
  const { accountId } = useParams();
  const [amount, setAmount] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  async function handleSubmit(event) {
    event.preventDefault();
    setError('');

    const response = await fetch(`http://localhost:8080/api/accounts/${accountId}/deposit`, {
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
      <h1>Deposit</h1>
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
    </div>
  );
}

export default Deposit;