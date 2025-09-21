import React, { useState } from "react";

export default function RegisterForm({ onRegister, onSwitchToLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const submit = (e) => {
    e.preventDefault();
    if (!username || !password) {
      setError("Please fill in all fields.");
      setSuccess("");
      return;
    }
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    if (users.some(u => u.username === username)) {
      setError("Username already registered!");
      setSuccess("");
      return;
    }
    users.push({ username, password });
    localStorage.setItem("users", JSON.stringify(users));
    setSuccess("Registered successfully! Please login.");
    setError("");
    setUsername("");
    setPassword("");
    setTimeout(onRegister, 1200);
  };

  return (
    <div className="auth-page">
      <form onSubmit={submit}>
        <h2>Register</h2>
        <label>
          Username
          <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Choose a username" />
        </label>
        <label>
          Password
          <input type="password" value={password} onChange={e => setPassword(e.target.value)} placeholder="Choose a password" />
        </label>
        <button type="submit">Register</button>
        {error && <p className="error-message">{error}</p>}
        {success && <p className="success-message">{success}</p>}
      </form>
      <div style={{ textAlign: "center", marginTop: "15px" }}>
        <button onClick={onSwitchToLogin} style={{ background:"none", border:"none", color:"#007bff", cursor:"pointer" }}>
          Back to Login
        </button>
      </div>
    </div>
  );
}
