import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authFetch } from '../utils/auth';
import { API_BASE_URL } from '../utils/api';

function CreateAccount() {
  const [accountType, setAccountType] = useState('SAVINGS');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  async function handleSubmit(event) {
    event.preventDefault();
    setError('');

    const meResponse = await authFetch(`${API_BASE_URL}/api/auth/me`);
    const me = await meResponse.json();

    const accountResponse = await authFetch(`${API_BASE_URL}/api/accounts`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userId: me.id, accountType }),
    });

    const account = await accountResponse.json();

    if (!accountResponse.ok) {
      setError(account.error || 'Could not create account');
      return;
    }

    navigate(`/accounts/${account.id}`);
  }

  return (
    <div>
      <h1>Create Account</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Account Type</label>
          <select value={accountType} onChange={(e) => setAccountType(e.target.value)}>
            <option value="SAVINGS">Savings</option>
            <option value="CHECKING">Checking</option>
          </select>
        </div>
        <button type="submit">Create Account</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

export default CreateAccount;
