import random
import math

BOT_NAME = "INSERT NAME FOR YOUR BOT HERE"


class RandomAgent:
    """Agent that picks a random available move.  You should be able to beat it."""
    def get_move(self, state, depth=None):
        return random.choice(state.successors())


class HumanAgent:
    """Prompts user to supply a valid move."""
    def get_move(self, state, depth=None):
        move__state = dict(state.successors())
        prompt = "Kindly enter your move {}: ".format(sorted(move__state.keys()))
        move = None
        while move not in move__state:
            try:
                move = int(input(prompt))
            except ValueError:
                continue
        return move, move__state[move]


class MinimaxAgent:
    """Artificially intelligent agent that uses minimax to optimally select the best move."""

    def get_move(self, state, depth=None):
        """Select the best available move, based on minimax value."""
        nextp = state.next_player()
        best_util = -math.inf if nextp == 1 else math.inf
        best_move = None
        best_state = None

        for move, state in state.successors():
            util = self.minimax(state, depth)
            if ((nextp == 1) and (util > best_util)) or ((nextp == -1) and (util < best_util)):
                best_util, best_move, best_state = util, move, state
        return best_move, best_state

    def minimax(self, state, depth):
        if state.is_full():
            return state.score()
        turn = state.next_player()
        if turn == 1:
            score = -math.inf
            for move, next_s in state.successors():
                succScore = self.minimax(next_s, depth)
                if(succScore > score):
                    score = succScore
            return score
        else:
            score = math.inf
            for move, next_s in state.successors():
                succScore = self.minimax(next_s, depth)
                if(succScore < score):
                    score = succScore
            return score

class HeuristicAgent(MinimaxAgent):
    """Artificially intelligent agent that uses depth-limited minimax to select the best move."""

    def minimax(self, state, depth):
        return self.minimax_depth(state, depth)

    def minimax_depth(self, state, depth):
        if state.is_full() or depth == 0:
            return self.evaluation(state)

        turn = state.next_player()
        if turn == 1:
            score = -math.inf
            for move, next_s in state.successors():
                t = depth
                if depth != None:
                    t = depth - 1
                succScore = self.minimax_depth(next_s, t)
                if(succScore > score):
                    score = succScore
            return score
        else:
            score = math.inf
            for move, next_s in state.successors():
                t = depth
                if depth != None:
                    t = depth - 1
                succScore = self.minimax_depth(next_s, t)
                if(succScore < score):
                    score = succScore
            return score

    def getRuns(self, row):
        score = 0
        streak = 0
        for r in row:
            if r == 1:
                streak = 0
            if r == 0:
                streak += 1
                score = score - (streak*2)
            else:
                streak += 1
                score = score - streak**2
        streak = 0
        for r in row:
            if r == -1:
                streak = 0
            if r == 0:
                streak += 1
                score = score + (streak*2)
            else:
                streak += 1
                score = score + streak**2
        return score

    def evaluation(self, state):
        rows = state.get_all_rows()
        score = 0
        for r in rows:
            score = score + self.getRuns(r)
        cols = state.get_all_cols()
        for c in cols:
            score = score + self.getRuns(c)
        diags = state.get_all_diags()
        for d in diags:
            score = score + self.getRuns(d)
        return score


class PruneAgent(HeuristicAgent):
    """Smarter computer agent that uses minimax with alpha-beta pruning to select the best move."""

    def minimax(self, state, depth):
        return self.minimax_prune(state, depth)

    def minimax_prune(self, state, depth, alpha=-math.inf, beta=math.inf):
        if state.is_full() or depth == 0:
            return self.evaluation(state)

        turn = state.next_player()
        if turn == 1:
            score = -math.inf
            for move, next_s in state.successors():
                t = depth
                if depth != None:
                    t = depth - 1
                succScore = self.minimax_prune(next_s, t, alpha, beta)
                if(succScore > score):
                    score = succScore
                alpha = max(score, alpha)
                if beta <= alpha:
                    break
            return score
        else:
            score = math.inf
            for move, next_s in state.successors():
                t = depth
                if depth != None:
                    t = depth - 1
                succScore = self.minimax_prune(next_s, t, alpha, beta)
                if(succScore < score):
                    score = succScore
                beta = min(score, beta)
                if beta <= alpha:
                    break
            return score


