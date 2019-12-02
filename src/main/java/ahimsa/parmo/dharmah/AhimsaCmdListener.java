package ahimsa.parmo.dharmah;

import ahimsa.parmo.dharmah.eval.*;
import io.vavr.collection.List;
import org.antlr.v4.runtime.tree.TerminalNode;

public class AhimsaCmdListener extends AhimsaBaseListener {

    private EvalPipe.EvalPipeBuilder evalPipeBuilder;

    @Override
    public void enterCommand(AhimsaParser.CommandContext ctx) {
        evalPipeBuilder = EvalPipe.builder(ctx.FILENAME().getText());
    }

    @Override
    public void enterCount(AhimsaParser.CountContext ctx) {
        evalPipeBuilder.after(new Count());
    }

    @Override
    public void enterSplitBy(AhimsaParser.SplitByContext ctx) {
        evalPipeBuilder.after(new SplitBy(ctx.WORD().getText()));
    }

    @Override
    public void enterReplace(AhimsaParser.ReplaceContext ctx) {
        evalPipeBuilder.after(new Replace(ctx.WORD(0).getText(), ctx.WORD(1).getText()));
    }

    @Override
    public void enterTakeColumns(AhimsaParser.TakeColumnsContext ctx) {
        evalPipeBuilder.after(new TakeColumns(List.ofAll(ctx.WORD()).map(TerminalNode::getText)));
    }

    public void evaluate() {
        evalPipeBuilder.build().eval();
    }
}
