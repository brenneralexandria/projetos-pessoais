import express from 'express';
import errorHandler from './middlewares/error-handler.middleware';
import statusRoute from './routes/status.route';
import usersRoute from './routes/users.route';

// A aplicação está instânciada
const app = express();

// Configuração da aplicação
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Configuração de rota
app.use(statusRoute);
app.use(usersRoute);

// configuração dos handels de erro
app.use(errorHandler);

// Todo o HTTP que chegar na porta 3000 vai ser resolvida pela nossa aplicação.
app.listen(3000, () => {
    console.log('Aplicação rodando 100%');
});