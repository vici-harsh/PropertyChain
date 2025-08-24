// src/RequireAccount.js
import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getCurrentAccount } from "./services/api"; // <-- note the path

export default function RequireAccount({ children }) {
  const acct = getCurrentAccount();
  const loc = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    if (!acct) {
      navigate("/account", { replace: true, state: { from: loc } });
    }
  }, [acct, loc, navigate]);

  if (!acct) return null; // we just redirected
  return children;
}
