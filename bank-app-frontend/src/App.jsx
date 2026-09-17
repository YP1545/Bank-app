import { Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import ProtectedRoute from './components/ProtectedRoute';
import AdminRoute from './components/AdminRoute';
import Admin from './pages/Admin';

import Login from './pages/Login';
import Register from './pages/Register';
import Home from './pages/Home';
import About from './pages/About';
import CreateAccount from './pages/CreateAccount';
import MyAccounts from './pages/MyAccounts';
import AccountDetails from './pages/AccountDetails';
import Deposit from './pages/Deposit';
import Withdraw from './pages/Withdraw';
import TransactionHistory from './pages/TransactionHistory';

function App() {
  return (
    <>
      <Header />
      <main>
        <Routes>
          {/* Public */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* Everything else requires login */}
          <Route path="/admin" element={<AdminRoute><Admin /></AdminRoute>} />
          <Route path="/" element={<ProtectedRoute><Home /></ProtectedRoute>} />
          <Route path="/about" element={<ProtectedRoute><About /></ProtectedRoute>} />
          <Route path="/create-account" element={<ProtectedRoute><CreateAccount /></ProtectedRoute>} />
          <Route path="/my-accounts" element={<ProtectedRoute><MyAccounts /></ProtectedRoute>} />
          <Route path="/accounts/:accountId" element={<ProtectedRoute><AccountDetails /></ProtectedRoute>} />
          <Route path="/accounts/:accountId/deposit" element={<ProtectedRoute><Deposit /></ProtectedRoute>} />
          <Route path="/accounts/:accountId/withdraw" element={<ProtectedRoute><Withdraw /></ProtectedRoute>} />
          <Route path="/accounts/:accountId/transactions" element={<ProtectedRoute><TransactionHistory /></ProtectedRoute>} />
        </Routes>
      </main>
      <Footer />
    </>
  );
}

export default App;
