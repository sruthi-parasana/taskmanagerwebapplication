import React, { useState } from "react";

export default function ForgotPassword({ onReset, onSwitchToLogin }) {
  const [username, setUsername] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const submit = (e) => {
    e.preventDefault();
    if (!username || !newPassword) {
      setError("Fill both fields");
      setSuccess("");
      return;
    }
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    const idx = users.findIndex(u => u.username === username);
    if (idx === -1) {
      setError("User not found.");
      setSuccess("");
      return;
    }
    users[idx].password = newPassword;
    localStorage.setItem("users", JSON.stringify(users));
    setSuccess("Password reset successful! Please login.");
    setError("");
    setUsername("");
    setNewPassword("");
    setTimeout(onReset, 1200);
  };

  return (
    <div className="auth-page">
      <form onSubmit={submit}>
        <h2>Reset Password</h2>
        <label>
          Username
          <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Enter your username" />
        </label>
        <label>
          New Password
          <input type="password" value={newPassword} onChange={e => setNewPassword(e.target.value)} placeholder="Enter a new password" />
        </label>
        <button type="submit">Reset Password</button>
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
