import { Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './pages/Home';
import About from './pages/About';
import Contact from './pages/Contact';
import Data from './pages/Data';
import CreateAccount from './pages/CreateAccount';
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
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="/contact" element={<Contact />} />
          <Route path="/data" element={<Data />} />
          <Route path="/create-account" element={<CreateAccount />} />
          <Route path="/accounts/:accountId" element={<AccountDetails />} />
          <Route path="/accounts/:accountId/deposit" element={<Deposit />} />
          <Route path="/accounts/:accountId/withdraw" element={<Withdraw />} />
          <Route path="/accounts/:accountId/transactions" element={<TransactionHistory />} />
        </Routes>
      </main>
      <Footer />
    </>
  );
}

export default App;