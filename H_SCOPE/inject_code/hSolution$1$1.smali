###### Class com.tpcstld.twozerogame.hSolution.AnonymousClass1.RunnableC00051 (com.tpcstld.twozerogame.hSolution$1$1)
.class Lcom/tpcstld/twozerogame/hSolution$1$1;
.super Ljava/lang/Object;
.source "hSolution.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/tpcstld/twozerogame/hSolution$1;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/tpcstld/twozerogame/hSolution$1;


# direct methods
.method constructor <init>(Lcom/tpcstld/twozerogame/hSolution$1;)V
    .registers 2

    .line 76
    iput-object p1, p0, Lcom/tpcstld/twozerogame/hSolution$1$1;->this$1:Lcom/tpcstld/twozerogame/hSolution$1;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 5

    .line 79
    iget-object v0, p0, Lcom/tpcstld/twozerogame/hSolution$1$1;->this$1:Lcom/tpcstld/twozerogame/hSolution$1;

    iget-object v0, v0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    iget v0, v0, Lcom/tpcstld/twozerogame/hSolution;->ret:I

    const-string v1, "hSolution"

    const/4 v2, 0x1

    if-eqz v0, :cond_44

    .line 80
    iget-object v0, p0, Lcom/tpcstld/twozerogame/hSolution$1$1;->this$1:Lcom/tpcstld/twozerogame/hSolution$1;

    iget-object v0, v0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    invoke-virtual {v0}, Lcom/tpcstld/twozerogame/hSolution;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    const-string v3, "verify failed"

    invoke-static {v0, v3, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 81
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v2, "verify failed, ret : "

    invoke-direct {v0, v2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v2, p0, Lcom/tpcstld/twozerogame/hSolution$1$1;->this$1:Lcom/tpcstld/twozerogame/hSolution$1;

    iget-object v2, v2, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    iget v2, v2, Lcom/tpcstld/twozerogame/hSolution;->ret:I

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 83
    new-instance v0, Ljava/lang/InterruptedException;

    invoke-direct {v0}, Ljava/lang/InterruptedException;-><init>()V

    .line 84
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 85
    iget-object v0, p0, Lcom/tpcstld/twozerogame/hSolution$1$1;->this$1:Lcom/tpcstld/twozerogame/hSolution$1;

    iget-object v0, v0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    invoke-virtual {v0}, Lcom/tpcstld/twozerogame/hSolution;->exitApp()V

    goto :goto_58

    .line 88
    :cond_44
    iget-object v0, p0, Lcom/tpcstld/twozerogame/hSolution$1$1;->this$1:Lcom/tpcstld/twozerogame/hSolution$1;

    iget-object v0, v0, Lcom/tpcstld/twozerogame/hSolution$1;->this$0:Lcom/tpcstld/twozerogame/hSolution;

    invoke-virtual {v0}, Lcom/tpcstld/twozerogame/hSolution;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    const-string v3, "verify success"

    invoke-static {v0, v3, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 89
    invoke-static {v1, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    :goto_58
    return-void
.end method
