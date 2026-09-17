# Bank App — Frontend

React + Vite frontend for the Simple Bank Application, backed by your Spring Boot + MongoDB API.

## What this build does

- Logged-out visitors see **only** Login (with a link to Register) — every other route redirects to `/login`.
- Once logged in: **Home, About, Create Account, My Accounts**, plus Logout.
- **My Accounts** lists every account you own → click one → **Account Details** → Deposit / Withdraw / View Transactions.
- Creating an account no longer asks for name/email — it uses your already-logged-in identity via `GET /api/auth/me`.

## Setup

```bash
npm install
npm run dev
```

Opens at `http://localhost:5173`. Your Spring Boot backend must be running at `http://localhost:8080` (change `API_BASE_URL` in `src/utils/api.js` if yours differs).

## Backend requirements — confirm these exist before testing

This frontend assumes your backend already has, from earlier in the build:

- `CorsConfig` wired through Spring Security (`.cors(cors -> cors.configurationSource(...))` in `SecurityConfig`, **not** a standalone `WebMvcConfigurer` — that approach stops working once Security is added)
- `POST /api/auth/register`, `POST /api/auth/login`, `GET /api/auth/me`
- `GET /api/accounts/my` — returns the logged-in user's own accounts (needs `AccountRepository.findByUserId`, a `UserService.getUserByEmail` helper, and an `AccountService.getAccountsByUserId` wrapper, wired into `AccountController`)
- `GET /api/accounts/{id}` returning `userName` directly in the response (avoids the frontend needing a separate, admin-only `/api/users/{id}` call)
- `SecurityConfig`: `/api/auth/**` public, `/api/users/**` requires `ROLE_ADMIN`, everything else requires just being logged in

If any of these are missing, that's the one backend gap to close — everything else is unchanged from what you already built and tested.

## Folder structure

```
src/
  components/   Header, Navbar, Footer, ProtectedRoute
  pages/        Login, Register, Home, About, CreateAccount,
                MyAccounts, AccountDetails, Deposit, Withdraw, TransactionHistory
  utils/        auth.js (token storage + authFetch), api.js (base URL)
  App.jsx       routes — everything but /login and /register is protected
  main.jsx      entry point, wraps App in BrowserRouter
  index.css     global styles
```
