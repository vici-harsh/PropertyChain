import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { setCurrentAccount } from "../services/api";

const ETH_ADDR_RE = /^0x[a-fA-F0-9]{40}$/;

export default function SelectAccount() {
  const nav = useNavigate();
  const loc = useLocation();
  const [addr, setAddr] = useState("");
  const [err, setErr] = useState("");

  const onSubmit = (e) => {
    e.preventDefault();
    if (!ETH_ADDR_RE.test(addr)) {
      setErr("Please enter a valid Ethereum address (0x...)");
      return;
    }
    setCurrentAccount(addr);
    const to = loc.state?.from?.pathname || "/";
    nav(to);
  };

  return (
    <div style={{ maxWidth: 420, margin: "80px auto" }}>
      <h2>Select your Ganache account</h2>
      <p style={{ color: "#555" }}>
        Paste an address from the Ganache GUI (Accounts list).
      </p>
      <form onSubmit={onSubmit}>
        <input
          style={{ width: "100%", padding: 10, fontSize: 14 }}
          placeholder="0xabc... (your account address)"
          value={addr}
          onChange={(e) => setAddr(e.target.value.trim())}
        />
        {err && <div style={{ color: "red", marginTop: 8 }}>{err}</div>}
        <button type="submit" style={{ marginTop: 12 }}>Continue</button>
      </form>
    </div>
  );
}
