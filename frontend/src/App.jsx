import { useState, useEffect, useCallback } from 'react';
import './App.css';

const WORDS = [
  { word: 'JAVA', hint: 'Linguagem de programação orientada a objetos' },
  { word: 'INTELLIJ', hint: 'Ambiente de desenvolvimento integrado (IDE)' },
  { word: 'GIT', hint: 'Sistema de controle de versão distribuído' },
  { word: 'LINUX', hint: 'Sistema operacional de código aberto' },
  { word: 'REACT', hint: 'Biblioteca para construir interfaces de usuário' },
];

export default function App() {
  const [wordData, setWordData] = useState(WORDS[0]);
  const [hidden, setHidden] = useState([]);
  const [attempted, setAttempted] = useState([]);
  const [life, setLife] = useState(6);
  const [status, setStatus] = useState('playing'); // playing | won | lost
  const [input, setInput] = useState('');
  const [msg, setMsg] = useState('');

  const reset = useCallback(() => {
    const w = WORDS[Math.floor(Math.random() * WORDS.length)];
    setWordData(w);
    setHidden(w.word.split('').map(() => '_'));
    setAttempted([]);
    setLife(6);
    setStatus('playing');
    setMsg('');
  }, []);

  useEffect(() => { reset(); }, [reset]);

  useEffect(() => {
    if (status !== 'playing') return;
    if (!hidden.includes('_')) setStatus('won');
    if (life <= 0) setStatus('lost');
  }, [hidden, life, status]);

  const handleGuess = (letter) => {
    if (status !== 'playing') return;
    letter = letter.toUpperCase();
    if (letter.length !== 1 || !/[A-Z]/.test(letter)) return;
    if (attempted.includes(letter)) { setMsg('Você já tentou essa letra!'); return; }
    const newAttempted = [...attempted, letter];
    setAttempted(newAttempted);
    const correct = wordData.word.includes(letter);
    if (correct) {
      const newHidden = hidden.map((c, i) => wordData.word[i] === letter ? letter : c);
      setHidden(newHidden);
      setMsg('Boa! Acertou a letra.');
    } else {
      setLife((l) => l - 1);
      setMsg('Errou! Perdeu 1 vida.');
    }
  };

  useEffect(() => {
    const onKey = (e) => {
      if (e.key === 'Enter') return;
      if (/^[a-zA-Z]$/.test(e.key)) handleGuess(e.key);
    };
    window.addEventListener('keydown', onKey);
    return () => window.removeEventListener('keydown', onKey);
  }, [status, hidden, attempted, wordData, life]);

  return (
    <div className="app">
      <header className="hero">
        <h1>Jogo da Forca</h1>
        <p className="subtitle">Adivinhe a palavra antes que seja tarde</p>
      </header>

      <main className="card">
        <div className="status-row">
          <span className="badge lives">❤️ {life}</span>
          <span className="badge attempted">Tentadas: {attempted.join(', ') || '-'}</span>
        </div>

        <div className="word-display">
          {hidden.map((ch, i) => (
            <span key={i} className={`letter ${ch === '_' ? 'empty' : 'filled'}`}>{ch}</span>
          ))}
        </div>

        <div className="hint">💡 Dica: {wordData.hint}</div>

        {msg && <div className="msg">{msg}</div>}

        {status === 'won' && (
          <div className="overlay won">
            <h2>PARABÉNS! Você venceu!</h2>
            <p>A palavra era: <strong>{wordData.word}</strong></p>
            <button onClick={reset}>Jogar novamente</button>
          </div>
        )}
        {status === 'lost' && (
          <div className="overlay lost">
            <h2>GAME OVER!</h2>
            <p>A palavra era: <strong>{wordData.word}</strong></p>
            <button onClick={reset}>Tentar de novo</button>
          </div>
        )}

        <div className="keyboard">
          {'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('').map((ch) => (
            <button
              key={ch}
              onClick={() => handleGuess(ch)}
              disabled={attempted.includes(ch) || status !== 'playing'}
              className={attempted.includes(ch) ? 'pressed' : ''}
            >{ch}</button>
          ))}
        </div>
      </main>
    </div>
  );
}
