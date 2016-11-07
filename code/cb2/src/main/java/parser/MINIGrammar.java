/* Generated By:JavaCC: Do not edit this line. MINIGrammar.java */
package parser;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import components.*;
import components.interfaces.*;
import java.util.ArrayList;
import testsuite.MINIException;
import errorHandling.ErrorHandler;

@SuppressWarnings("unused")
public class MINIGrammar implements MINIGrammarConstants {
  public static ArrayList < ClassNode > parse(File in) throws MINIException
  {
    try
    {
      MINIGrammar parser = new MINIGrammar(new FileInputStream(in));
      return parser.file();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      return null;
    }
    catch (ParseException e)
    {
      ErrorHandler.handleParseError(in, e.currentToken, e.tokenImage, e.expectedTokenSequences);
      return null;
    }
    catch (TokenMgrError e) {
                ErrorHandler.handleTokenMgrError(in, e);
                return null;
    }
  }

  final public ArrayList < ClassNode > file() throws ParseException {
  ArrayList < ClassNode > classes = new ArrayList < ClassNode > ();
  ClassNode cls;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CLASS:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      cls = mini_class();
      classes.add(cls);
    }
    jj_consume_token(0);
    {if (true) return classes;}
    throw new Error("Missing return statement in function");
  }

  final public ClassNode mini_class() throws ParseException {
  ClassNode cls;
  MemberNode classMember;
    jj_consume_token(CLASS);
    jj_consume_token(ID);
           cls = new ClassNode(token);
    jj_consume_token(BRACE_OPEN);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      classMember = classMember();
      cls.children.add(classMember);
    }
    jj_consume_token(BRACE_CLOSE);
    {if (true) return cls;}
    throw new Error("Missing return statement in function");
  }

  final public MemberNode classMember() throws ParseException {
  MethodNode m;
  Token memberName;
  Type type;
    type = type();
    memberName = jj_consume_token(ID);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SEMICOLON:
      jj_consume_token(SEMICOLON);
      {if (true) return new FieldNode(memberName, type);}
      break;
    case PARAN_OPEN:
      m = method(memberName, type);
      {if (true) return m;}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public MethodNode method(Token name, Type type) throws ParseException {
  BlockNode body;
  ArrayList<NamedType> arguments;
    arguments = signature();
    body = blockStatement();
    {if (true) return new MethodNode(name, type, body);}
    throw new Error("Missing return statement in function");
  }

  final public Type type() throws ParseException {
  Token type;
  int dimensions = 0;
    type = jj_consume_token(ID);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ARRAY_DEF:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      jj_consume_token(ARRAY_DEF);
                                 dimensions++;
    }
    {if (true) return new Type(type, dimensions);}
    throw new Error("Missing return statement in function");
  }

  final public ArrayList < NamedType > signature() throws ParseException {
  ArrayList < NamedType > arguments = new ArrayList < NamedType > ();
  Type type;
    jj_consume_token(PARAN_OPEN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      type = type();
      jj_consume_token(ID);
      arguments.add(new NamedType(token, type));
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[4] = jj_gen;
          break label_4;
        }
        jj_consume_token(COMMA);
        type = type();
        jj_consume_token(ID);
        arguments.add(new NamedType(token, type));
      }
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    jj_consume_token(PARAN_CLOSE);
    {if (true) return arguments;}
    throw new Error("Missing return statement in function");
  }

  final public StatementNode statement() throws ParseException {
  StatementNode s = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BRACE_OPEN:
      s = blockStatement();
      break;
    case IF:
      s = ifStatement();
      break;
    case WHILE:
      s = whileStatement();
      break;
    case RETURN:
      s = returnStatement();
      break;
    case PARAN_OPEN:
    case THIS:
    case NULL:
    case NEW:
    case MINUS:
    case NEGATION:
    case ID:
    case INT:
    case BOOL:
    case STRING:
      s = simpleStatement();
      break;
    case VAR:
      s = declaration();
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  final public StatementNode simpleStatement() throws ParseException {
  ExpressionNode first, second;
    first = expression7();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SEMICOLON:
      jj_consume_token(SEMICOLON);
                    {if (true) return new SimpleStatementNode(first);}
      break;
    case ASSIGNMENT:
      jj_consume_token(ASSIGNMENT);
      second = expression7();
      jj_consume_token(SEMICOLON);
                                                             {if (true) return new AssignmentStatementNode(first, second);}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public DeclarationStatementNode declaration() throws ParseException {
  Token name;
  ExpressionNode expression;
    jj_consume_token(VAR);
    jj_consume_token(ID);
           name = token;
    jj_consume_token(ASSIGNMENT);
    expression = expression7();
    jj_consume_token(SEMICOLON);
    {if (true) return new DeclarationStatementNode(name, expression);}
    throw new Error("Missing return statement in function");
  }

  final public BlockNode blockStatement() throws ParseException {
  BlockNode block = new BlockNode();
  StatementNode statement;
    jj_consume_token(BRACE_OPEN);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BRACE_OPEN:
      case PARAN_OPEN:
      case THIS:
      case NULL:
      case NEW:
      case RETURN:
      case VAR:
      case IF:
      case WHILE:
      case MINUS:
      case NEGATION:
      case ID:
      case INT:
      case BOOL:
      case STRING:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_5;
      }
      statement = statement();
      block.children.add(statement);
    }
    jj_consume_token(BRACE_CLOSE);
    {if (true) return block;}
    throw new Error("Missing return statement in function");
  }

  final public IfNode ifStatement() throws ParseException {
  ExpressionNode condition;
  BlockNode first, second = null;
    jj_consume_token(IF);
    jj_consume_token(PARAN_OPEN);
    condition = expression7();
    jj_consume_token(PARAN_CLOSE);
    first = blockStatement();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ELSE:
      jj_consume_token(ELSE);
      second = blockStatement();
      break;
    default:
      jj_la1[9] = jj_gen;
      ;
    }
    {if (true) return new IfNode(condition, first, second);}
    throw new Error("Missing return statement in function");
  }

  final public WhileNode whileStatement() throws ParseException {
  ExpressionNode condition;
  BlockNode body;
    jj_consume_token(WHILE);
    jj_consume_token(PARAN_OPEN);
    condition = expression7();
    jj_consume_token(PARAN_CLOSE);
    body = blockStatement();
    {if (true) return new WhileNode(condition, body);}
    throw new Error("Missing return statement in function");
  }

  final public ReturnNode returnStatement() throws ParseException {
  ExpressionNode returnValue;
    jj_consume_token(RETURN);
    returnValue = expression7();
    jj_consume_token(SEMICOLON);
    {if (true) return new ReturnNode(returnValue);}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression7() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
    current = expression6();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_6;
      }
      jj_consume_token(OR);
      next = expression6();
                                                      current = new OrBinaryExpressionNode(token, current, next);
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression6() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
    current = expression5();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_7;
      }
      jj_consume_token(AND);
      next = expression5();
                                                       current = new AndBinaryExpressionNode(token,current,next);
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression5() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
    current = expression4();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQUAL:
      case NOTEQUAL:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_8;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQUAL:
        jj_consume_token(EQUAL);
        next = expression4();
                                      current= new EqBinaryExpressionNode(token, current, next);
        break;
      case NOTEQUAL:
        jj_consume_token(NOTEQUAL);
        next = expression4();
                                           current= new NeqBinaryExpressionNode(token, current, next);
        break;
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression4() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
    current = expression3();
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LESS_THAN_EQUAL:
      case GREATER_THAN_EQUAL:
      case LESS_THAN:
      case GREATER_THAN:
        ;
        break;
      default:
        jj_la1[14] = jj_gen;
        break label_9;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LESS_THAN_EQUAL:
        jj_consume_token(LESS_THAN_EQUAL);
        next = expression3();
                                              current = new LteBinaryExpressionNode(token, current, next);
        break;
      case GREATER_THAN_EQUAL:
        jj_consume_token(GREATER_THAN_EQUAL);
        next = expression3();
                                                   current = new GteBinaryExpressionNode(token, current, next);
        break;
      case LESS_THAN:
        jj_consume_token(LESS_THAN);
        next = expression3();
                                          current = new LtBinaryExpressionNode(token, current, next);
        break;
      case GREATER_THAN:
        jj_consume_token(GREATER_THAN);
        next = expression3();
                                             current = new GtBinaryExpressionNode(token, current, next);
        break;
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression3() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
    current = expression2();
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MINUS:
      case PLUS:
        ;
        break;
      default:
        jj_la1[16] = jj_gen;
        break label_10;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        jj_consume_token(PLUS);
        next = expression2();
                                   current = new PlusBinaryExpressionNode(token, current, next);
        break;
      case MINUS:
        jj_consume_token(MINUS);
        next = expression2();
                                      current = new MinusBinaryExpressionNode(token, current, next);
        break;
      default:
        jj_la1[17] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression2() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
    current = expression1();
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
      case DIVIDE:
      case REMAINDER:
        ;
        break;
      default:
        jj_la1[18] = jj_gen;
        break label_11;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
        jj_consume_token(MULTIPLY);
        next = expression1();
                                       current = new MultiplyBinaryExpressionNode(token, current, next);
        break;
      case DIVIDE:
        jj_consume_token(DIVIDE);
        next = expression1();
                                       current = new DivideBinaryExpressionNode(token, current, next);
        break;
      case REMAINDER:
        jj_consume_token(REMAINDER);
        next = expression1();
                                          current = new RemainderBinaryExpressionNode(token, current, next);
        break;
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression1() throws ParseException {
  ExpressionNode current;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
    case NEGATION:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NEGATION:
        jj_consume_token(NEGATION);
        current = expression1();
                                              current = new NegationUnaryExpressionNode(token,current);
        break;
      case MINUS:
        jj_consume_token(MINUS);
        current = expression1();
                                             current = new MinusUnaryExpressionNode(token, current);
        break;
      default:
        jj_la1[20] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    case PARAN_OPEN:
    case THIS:
    case NULL:
    case NEW:
    case ID:
    case INT:
    case BOOL:
    case STRING:
      current = expression0();
      break;
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression0() throws ParseException {
  ExpressionNode current;
    current = atomicExpression();
    label_12:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DOT:
        ;
        break;
      default:
        jj_la1[22] = jj_gen;
        break label_12;
      }
      jj_consume_token(DOT);
      jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PARAN_OPEN:
        current = argumentList(token, current);
        break;
      default:
        jj_la1[23] = jj_gen;
        ;
      }
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

/*
ExpressionNode expression() :
{
  ExpressionNode n;
  UnaryExpressionNode unary;
}
{
  (
    unary = unaryOperator() unary.child = expression()
    {
      return unary;
    }
  | n = atomicExpression() (n = expression_suffix(n))?
  )
  {
    return n;
  }
}

ExpressionNode expression_suffix(ExpressionNode prefix) :
{
  BinaryExpressionNode b;
  MemberExpressionNode m = null;
}
{
    b=binaryOperator() b.second = expression()
	{
	  b.first = prefix;
	  b.balance();
	  return b;
	}
	| < DOT >
	  {
	    Token identifier;
	    ExpressionNode n=null;
	  }
	  identifier = < ID >
	  (m=argumentList(identifier))?
	  {
	    if(m == null){
	      m = new FieldMemberExpressionNode(identifier, prefix);
	    }
	  }
	  (n=expression_suffix(m))?
	  {
	    if(n != null)
	      return n;
	    return m;
	  }
}
UnaryExpressionNode unaryOperator() :
{}
{
  < MINUS > { return new MinusUnaryExpressionNode(token); }
| < NEGATION > { return new NegationUnaryExpressionNode(token); }
}

BinaryExpressionNode binaryOperator() :
{}
{
  < PLUS > { return new PlusBinaryExpressionNode(token); }
| < MINUS > { return new MinusBinaryExpressionNode(token); }
| < MULTIPLY > { return new MultiplyBinaryExpressionNode(token); }
| < DIVIDE > { return new DivideBinaryExpressionNode(token); }
| < REMAINDER > { return new RemainderBinaryExpressionNode(token); }
| < EQUAL > { return new EqBinaryExpressionNode(token); }
| < NOTEQUAL > { return new NeqBinaryExpressionNode(token); }
| < LESS_THAN_EQUAL > { return new LteBinaryExpressionNode(token); }
| < GREATER_THAN_EQUAL > { return new GteBinaryExpressionNode(token); }
| < LESS_THAN > { return new LtBinaryExpressionNode(token); }
| < GREATER_THAN > { return new GtBinaryExpressionNode(token); }
| < AND > { return new AndBinaryExpressionNode(token); }
| < OR > { return new OrBinaryExpressionNode(token); }
}
*/
  final public MethodInvocationExpressionNode argumentList(Token name, ExpressionNode child) throws ParseException {
  MethodInvocationExpressionNode n = new MethodInvocationExpressionNode(name, child);
  ExpressionNode expr;
    jj_consume_token(PARAN_OPEN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PARAN_OPEN:
    case THIS:
    case NULL:
    case NEW:
    case MINUS:
    case NEGATION:
    case ID:
    case INT:
    case BOOL:
    case STRING:
      expr = expression7();
      n.children.add(expr);
      label_13:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[24] = jj_gen;
          break label_13;
        }
        jj_consume_token(COMMA);
        expr = expression7();
        n.children.add(expr);
      }
      break;
    default:
      jj_la1[25] = jj_gen;
      ;
    }
    jj_consume_token(PARAN_CLOSE);
    {if (true) return n;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode atomicExpression() throws ParseException {
  ExpressionNode n;
  MemberExpressionNode m = null;
  Token identifier;
  ExpressionNode expression;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      identifier = jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PARAN_OPEN:
        m = argumentList(identifier, null);
        break;
      default:
        jj_la1[26] = jj_gen;
        ;
      }
      if(m == null){
            m = new FieldMemberExpressionNode(identifier, null);
          }
          {if (true) return m;}
      break;
    case THIS:
      jj_consume_token(THIS);
               n = new ThisAtomicExpressionNode(token);
      break;
    case STRING:
      jj_consume_token(STRING);
                 n = new StringAtomicExpressionNode(token);
      break;
    case INT:
      jj_consume_token(INT);
              n = new IntAtomicExpressionNode(token);
      break;
    case BOOL:
      jj_consume_token(BOOL);
               n = new BoolAtomicExpressionNode(token);
      break;
    case NULL:
      n = nullExpression();
      break;
    case NEW:
      n = newExpression();
      break;
    case PARAN_OPEN:
      jj_consume_token(PARAN_OPEN);
      expression = expression7();
      jj_consume_token(PARAN_CLOSE);
                      {if (true) return new PriorityExpressionNode(expression);}
      break;
    default:
      jj_la1[27] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  {if (true) return n;}
    throw new Error("Missing return statement in function");
  }

  final public NullExpressionNode nullExpression() throws ParseException {
  Type type;
    jj_consume_token(NULL);
    jj_consume_token(LESS_THAN);
    type = type();
    jj_consume_token(GREATER_THAN);
    {if (true) return new NullExpressionNode(type);}
    throw new Error("Missing return statement in function");
  }

  final public NewExpressionNode newExpression() throws ParseException {
  NewExpressionNode n;
  Type type;
    jj_consume_token(NEW);
    jj_consume_token(LESS_THAN);
    type = type();
                                        n = new NewExpressionNode(type);
    label_14:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[28] = jj_gen;
        break label_14;
      }
      jj_consume_token(COMMA);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
        jj_consume_token(INT);
        break;
      case ID:
        jj_consume_token(ID);
        break;
      default:
        jj_la1[29] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      n.arguments.add(token);
    }
    jj_consume_token(GREATER_THAN);
    {if (true) return n;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public MINIGrammarTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[30];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x400,0x0,0x82000,0x8000,0x40000,0x0,0x35f12800,0x8080000,0x35f12800,0x2000000,0x0,0x0,0x0,0x0,0x0,0x0,0x50000000,0x50000000,0x80000000,0x80000000,0x30000000,0x30312000,0x20000,0x2000,0x40000,0x30312000,0x2000,0x312000,0x40000,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x400,0x0,0x0,0x0,0x400,0x3c00,0x0,0x3c00,0x0,0x200,0x100,0xc,0xc,0xf0,0xf0,0x0,0x0,0x3,0x3,0x0,0x3c00,0x0,0x0,0x0,0x3c00,0x0,0x3c00,0x0,0xc00,};
   }

  /** Constructor with InputStream. */
  public MINIGrammar(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MINIGrammar(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MINIGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public MINIGrammar(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MINIGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public MINIGrammar(MINIGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(MINIGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 30; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[47];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 30; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 47; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
