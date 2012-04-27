package MyGCC;

public class Instruction {
  
  public Expression lexpr;
  public Expression rexpr;
  public InstructionType instrType;
  public String assemblyStr;
  public Context myContext = null;
  
  public Instruction(Expression r, InstructionType op){
    this.lexpr = null;
    this.rexpr = r;
    this.instrType = op;
    this.assemblyStr = null;
  }
  
  public Instruction(Expression l, Expression r, InstructionType op){
    this.lexpr = l;
    this.rexpr = r;
    this.instrType = op;
    this.assemblyStr = null;
  }
  
  /**
   * This functions return the highest number of parameters contained in a
   * function call of the instruction.
   * The value returned is <b>-1</b> if there's no call to functions in the instruction
   */
  public int maxParameters(){
    int n = -1;
    if (lexpr != null) n = Math.max(n, lexpr.maxParameters());
    if (rexpr != null) n = Math.max(n, rexpr.maxParameters());
    return n;
  }
  
  public static String instructionToAssembly(Instruction instruct, Context context) throws Exception{
		StringBuffer sb = new StringBuffer();
		Expression l = instruct.lexpr;
    Variable a;
    FunctionCall f;
    if(instruct != null){
      if(instruct.rexpr == null){
        System.out.println("rexpr is null");
        // do nothing: already handled in epilogue()
        return sb.toString();
      }
      
      System.out.println("Instruction type: " + instruct.instrType.toString());
      switch(instruct.instrType){
        
        case RETURN:
          a = (Variable)l;
					sb.append(instruct.rexpr.handleExpression(a, context));
          break;
          
        case EXIT:
          f = (FunctionCall)l;
          System.out.println("Exit detected");
          sb.append(instruct.rexpr.handleExpression(f, context));
          //TODO: do not generate ret/leave when function contains an exit and no RETURN instructions
          break;
          
        case EQL:
          a = (Variable)l;
          
          if(instruct.rexpr.isFullyNumeric()){
            System.out.println("Fully numeric found");
            sb.append("\t" + Assembly.MOV.toString() + "\t$" + ExpressionHelper.calculateNum(instruct.rexpr) + ", " + context.getVariableLocation(String.valueOf(a.getValue())) + "\n"); 
          }
          
          else{
            sb.append(instruct.rexpr.handleExpression(a, context));
            sb.append("\t" + Assembly.MOV.toString() + "\t" + Register.RAX.toString() + ", " + context.getVariableLocation(String.valueOf(a.getValue())) + "\n");
          }
          break;
          
        default:
          // case null, ie. for instructions like "1+2;"
          // optimize and don't calculate
          break;
      }
      instruct.assemblyStr = sb.toString();
      return instruct.assemblyStr;
    }
    
    else{
      System.err.println("ERROR: instruction is null");
      return null;
    }
  }
  
  
}