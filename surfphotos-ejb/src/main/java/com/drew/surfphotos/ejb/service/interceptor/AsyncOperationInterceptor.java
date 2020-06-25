package com.drew.surfphotos.ejb.service.interceptor;

import com.drew.surfphotos.model.AsyncOperation;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;

@Interceptor
public class AsyncOperationInterceptor {
    @AroundInvoke
    public Object aroundProcessImageResource(InvocationContext ic) throws Exception {
        replaceAsynOperationByProxy(ic);
        return ic.proceed();
    }

    private void replaceAsynOperationByProxy(InvocationContext ic) {
        Object[] params = ic.getParameters();
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof AsyncOperation) {
                params[i] = new AsyncOperationProxy((AsyncOperation) params[i]);
            }
        }
        ic.setParameters(params);
    }

    private static class AsyncOperationProxy implements AsyncOperation {

        private final AsyncOperation originalAsyncOperation;

        public AsyncOperationProxy(AsyncOperation originalAsyncOperation) {
            this.originalAsyncOperation = originalAsyncOperation;
        }

        @Override
        public void onSuccess(Object result) {
            originalAsyncOperation.onSuccess(result);
        }

        @Override
        public void onFailed(Throwable throwable) {
            try {
                originalAsyncOperation.onFailed(throwable);
            } catch (RuntimeException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "AsyncOperation.onFailed throws exception: " + e.getMessage(), e);
            }
        }

        @Override
        public long getTimeOutInMillis() {
            return originalAsyncOperation.getTimeOutInMillis();
        }
    }
}
