import React, { useState } from "react";

export default function LoginForm({ onLogin, onSwitchToRegister, onSwitchToForgot }) {  
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const submit = (e) => {
    e.preventDefault();
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    const user = users.find(u => u.username === username && u.password === password);
    if (user) {
      setError("");
      onLogin(username);
    } else {
      setError("Invalid username or password!");
    }
  };

  return (
    <div className="auth-page">
      <form onSubmit={submit}>
        <h2>Login</h2>
        <label>
          Username
          <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Enter your username" />
        </label>
        <label>
          Password
          <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Enter your password" />
        </label>
        <button type="submit">Login</button>
        {error && <p className="error-message">{error}</p>}
      </form>
      <div style={{ textAlign: "center", marginTop: "15px" }}>
        <button onClick={onSwitchToRegister} style={{ marginRight: "10px", background:"none", border:"none", color:"#007bff", cursor:"pointer" }}>
          Register
        </button>
        <button onClick={onSwitchToForgot} style={{ background:"none", border:"none", color:"#007bff", cursor:"pointer" }}>
          Forgot Password?
        </button>
      </div>
    </div>
  );
}
